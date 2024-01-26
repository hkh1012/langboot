package com.hkh.ai.chain.llm.capabilities.generation.function.openai;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.hkh.ai.chain.llm.capabilities.generation.function.FunctionChatService;
import com.hkh.ai.service.ConversationService;
import com.hkh.ai.service.MediaFileService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * openai函数调用实现
 * @author huangkh
 */
@Service
@Slf4j
public class OpenAiFunctionChatService implements FunctionChatService {
    @Value("${chain.llm.openai.model}")
    private String defaultModel;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @Override
    public String functionCompletion(String content,String functionName,String description ,Class clazz) {
        OpenAiService service = openAiServiceProxy.service();
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
        log.info("functionCompletion chatCompletionRequest ===> {}",chatCompletionRequest);
        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        log.info("functionCompletion ==> {}",chatCompletion.toString());
        ChatCompletionChoice chatCompletionChoice = chatCompletion.getChoices().get(0);
        ChatMessage message = chatCompletionChoice.getMessage();
        return JSONObject.toJSONString(message);
    }

    private FunctionExecutor getFunctionExecutor(String functionName,String description ,Class clazz){
        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name(functionName)
                .description(description)
                .executor(clazz, w -> w)
                .build()));
        return functionExecutor;
    }
}
