package com.hkh.ai.chain.llm.capabilities.generation.text.openai;

import cn.hutool.core.util.StrUtil;
import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.hkh.ai.service.MediaFileService;
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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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
    private ConversationService conversationService;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser) throws IOException {
        OpenAiService service = openAiServiceProxy.service();
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(request.getContent());

        final List<ChatMessage> messages = new ArrayList<>();
        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), request.getContent(), "Q");
        for (String content : nearestList) {
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), content);
            messages.add(systemMessage);
        }
        String ask = request.getContent();
        String temp = "";
        for (Conversation conversation : history){
            temp = temp + conversation.getContent();
        }
        ask = temp + ask;
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), ask + (nearestList.size() > 0 ? "\n\n注意：回答问题时，须严格根据我给你的系统上下文内容原文进行回答，请不要自己发挥,回答时保持原来文本的段落层级" : "" ));
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .temperature(0.1)
                .user(request.getSessionId())
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        StringBuilder sb = new StringBuilder();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(item -> {
                    if (StrUtil.isBlank(item.getChoices().get(0).getFinishReason())
                            && StrUtil.isBlank(item.getChoices().get(0).getMessage().getRole())){
                        String content = item.getChoices().get(0).getMessage().getContent();
                        if (content.endsWith("\n") || content.endsWith("\r")){
                            content = content.replaceAll("\n","<br>");
                            content = content.replaceAll("\r","<br>");
                        }
                        if (content.contains(" ")){
                            content = content.replaceAll(" ","&nbsp;");
                        }
                        sb.append(content);
                        sseEmitter.send(content);
                    }else if (StrUtil.isNotBlank(item.getChoices().get(0).getFinishReason())){
                        sseEmitter.send("[END]");
                        String fullContent = sb.toString();
                        List<Integer> completionToken = enc.encode(fullContent);
                        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), sb.toString(), "A");
                    }
                });
        service.shutdownExecutor();
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
