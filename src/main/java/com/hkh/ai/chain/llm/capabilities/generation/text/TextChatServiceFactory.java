package com.hkh.ai.chain.llm.capabilities.generation.text;

import com.hkh.ai.chain.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.chatglm2.Chatglm2TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.openai.OpenAiTextChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextChatServiceFactory {

    @Value("${chain.llm.text.type}")
    private String type;

    private final OpenAiTextChatService openAiTextChatService;
    private final Chatglm2TextChatService chatglm2TextChatService;
    private final BaiduQianFanTextChatService baiduQianFanTextChatService;

    public TextChatServiceFactory(
            OpenAiTextChatService openAiTextChatService,
            Chatglm2TextChatService chatglm2TextChatService,
            BaiduQianFanTextChatService baiduQianFanTextChatService) {
        this.openAiTextChatService = openAiTextChatService;
        this.chatglm2TextChatService = chatglm2TextChatService;
        this.baiduQianFanTextChatService = baiduQianFanTextChatService;
    }

    public TextChatService getTextChatService(){
        if("openai".equals(type)){
            return openAiTextChatService;
        }else if("chatglm2".equals(type)){
            return chatglm2TextChatService;
        }else if("baidu".equals(type)){
            return baiduQianFanTextChatService;
        }else {
            return null;
        }

    }
}

