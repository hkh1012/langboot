package com.hkh.ai.chain.llm.capabilities.generation.function;

import com.hkh.ai.chain.llm.capabilities.generation.function.openai.OpenAiFunctionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.chatglm2.Chatglm2TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.openai.OpenAiTextChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FunctionChatServiceFactory {

    @Value("${chain.llm.function.type}")
    private String type;

    @Autowired
    private OpenAiFunctionChatService openAiFunctionChatService;

    public FunctionChatServiceFactory(OpenAiFunctionChatService openAiFunctionChatService) {
        this.openAiFunctionChatService = openAiFunctionChatService;
    }

    public FunctionChatService getFunctionChatService(){
        if("openai".equals(type)){
            return openAiFunctionChatService;
        }else {
            return null;
        }

    }
}

