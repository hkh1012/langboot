package com.hkh.ai.chain.llm;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.JsonNode;
import com.hkh.ai.chain.retrieve.PromptRetrieverProperties;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.util.*;

@Service
@Primary
@Slf4j
public class OpenAiChatService implements ChatService {

    @Value("${chain.llm.openai.token}")
    private String apiToken;

    @Value("${chain.llm.openai.model}")
    private String defaultModel;

    @Value("${proxy.enable}")
    private boolean proxyEnable;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private String proxyPort;

    @Autowired
    private ConversationService conversationService;

    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser){

        OpenAiService service = new OpenAiService(apiToken);
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
        enableProxy();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(item -> {
                    if (StrUtil.isBlank(item.getChoices().get(0).getFinishReason())
                            && StrUtil.isBlank(item.getChoices().get(0).getMessage().getRole())){
                        String content = item.getChoices().get(0).getMessage().getContent();
                        System.out.print(content);
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
                        System.out.println("total token costs: " + (promptTokens.size() + completionToken.size()));
                        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), sb.toString(), "A");
                    }
                });
        ;
        service.shutdownExecutor();
    }

    @Override
    public String blockCompletion(String content) {
        enableProxy();
        OpenAiService service = new OpenAiService(apiToken, Duration.ofSeconds(300));
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
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion ==> {}",chatCompletion.toString());
        return chatCompletion.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public String functionCompletion(String content,String functionName,String description ,Class clazz) {
        enableProxy();
        OpenAiService service = new OpenAiService(apiToken, Duration.ofSeconds(300));
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(userMessage);

        FunctionExecutor functionExecutor = getFunctionExecutor(functionName,description,clazz);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .functions(functionExecutor.getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        log.info("functionCompletion ==> {}",chatCompletion.toString());
        JsonNode arguments = chatCompletion.getChoices().get(0).getMessage().getFunctionCall().getArguments();
        log.info("arguments ==> {}",arguments);
        return JSONObject.toJSONString(arguments);
    }

    private FunctionExecutor getFunctionExecutor(String functionName,String description ,Class clazz){
        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name(functionName)
                .description(description)
                .executor(clazz, w -> w)
                .build()));
        return functionExecutor;
    }

    private void enableProxy(){
        if (proxyEnable){
            System.getProperties().setProperty("https.proxyHost",proxyHost);
            System.getProperties().setProperty("https.proxyPort",proxyPort);
        }
    }

}
