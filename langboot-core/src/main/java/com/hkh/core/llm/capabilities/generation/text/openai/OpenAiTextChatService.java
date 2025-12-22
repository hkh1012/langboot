package com.hkh.core.llm.capabilities.generation.text.openai;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hkh.core.llm.OpenAiServiceProxy;
import com.hkh.core.llm.capabilities.generation.text.TextChatService;
import com.hkh.domain.dto.FluxStreamBuilder;
import com.hkh.domain.dto.FluxStreamDto;
import com.hkh.domain.dto.HistoryMessageDto;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * openai文本聊天服务
 * @author huangkh
 */
@Slf4j
@Service
public class OpenAiTextChatService implements TextChatService {

    @Value("${chain.llm.openai.model}")
    private String defaultModel;


    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;


    @Override
    public FluxStreamDto stream(String content, String systemPrompt, List<String> nearestList, List<HistoryMessageDto> historyList) {
        OpenAiService service = openAiServiceProxy.service();
        final List<ChatMessage> messages = new ArrayList<>();

        // 系统提示词
        StringBuilder nearestContext = new StringBuilder();
        for (String nearest : nearestList){
            nearestContext.append(nearest).append("\n");
        }
        if (!nearestContext.isEmpty()){
            systemPrompt = systemPrompt + "\n\n可供参考的资料如下:\n" + nearestContext;
        }
        if (StrUtil.isNotEmpty(systemPrompt)){
            ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt);
            messages.add(systemMessage);
        }

        // 历史聊天记录
        for (HistoryMessageDto historyMessageDto : historyList) {
            ChatMessage chatMessage = new ChatMessage(historyMessageDto.getRole(), historyMessageDto.getContent());
            messages.add(chatMessage);
        }

        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .temperature(0.1)
                .topP(0.3)
                .user(RandomUtil.randomString(32))
                .n(1)
                .logitBias(new HashMap<>())
                .build();

        FluxStreamDto fluxStreamDto = FluxStreamBuilder.build();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(item -> {
                    if (StrUtil.isBlank(item.getChoices().get(0).getFinishReason())
                            && StrUtil.isBlank(item.getChoices().get(0).getMessage().getRole())){
                        String messageContent = item.getChoices().get(0).getMessage().getContent();
                        fluxStreamDto.getSb().append(messageContent);
                        fluxStreamDto.getTextBlockingDeque().offer(messageContent);
                        fluxStreamDto.getAudioBlockingDeque().offer(messageContent);
                    }else if (StrUtil.isNotBlank(item.getChoices().get(0).getFinishReason())){
                        fluxStreamDto.getTextBlockingDeque().offer("[END]");
                        fluxStreamDto.getAudioBlockingDeque().offer("[END]");
                    }
                });
        service.shutdownExecutor();
        return fluxStreamDto;
    }

    @Override
    public String blockCompletion(String content) {
        OpenAiService service = openAiServiceProxy.service();
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion ==> {}",chatCompletion.toString());
        return chatCompletion.getChoices().get(0).getMessage().getContent();
    }
}
