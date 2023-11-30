package com.hkh.ai.chain.llm;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChatglmChatService implements ChatService {

    @Value("${chain.llm.chatglm.baseurl}")
    private String baseUrl;

    @Value("${chain.llm.chatglm.model}")
    private String defaultModel;

    @Autowired
    private ConversationService conversationService;

    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser,List<String> nearestExampleList){
        // 参考 OpenAi 库实现 Chatglm 流式对话
        ChatglmService service = new ChatglmService(baseUrl);

        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(request.getContent());
        System.out.println("promptTokens length == " + promptTokens.size());

        System.out.println("Streaming chat completion...");
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
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), ask);
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
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
//                        System.out.print(content);
                        if (content.contains("\n") || content.contains("\r")){
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
                        System.out.println("total token costs: " + (promptTokens.size() + completionToken.size()));
                        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), sb.toString(), "A");
                    }
                });
        service.shutdownExecutor();
    }

    @Override
    public String blockCompletion(String content) {
        ChatglmService service = new ChatglmService(baseUrl);
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .user(content)
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion ==> ",chatCompletion.toString());
        return chatCompletion.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public String functionCompletion(String content,String functionName,String description ,Class clazz) {
        return JSONObject.toJSONString(blockCompletion(content));
    }
}
