package com.hkh.ai.chain.llm;

import com.hkh.ai.chain.llm.baiduqianfan.BaiduQianFanChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatServiceFactory {

    @Value("${chain.llm.type}")
    private String type;

    private final OpenAiChatService openAiChatService;
    private final ChatglmChatService chatglmChatService;
    private final BaiduQianFanChatService baiduQianFanChatService;

    public ChatServiceFactory(OpenAiChatService openAiChatService, ChatglmChatService chatglmChatService, BaiduQianFanChatService baiduQianFanChatService) {
        this.openAiChatService = openAiChatService;
        this.chatglmChatService = chatglmChatService;
        this.baiduQianFanChatService = baiduQianFanChatService;
    }

    public ChatService getChatService(){
        if("openai".equals(type)){
            return openAiChatService;
        }else if("chatglm".equals(type)){
            return chatglmChatService;
        }else if("baidu".equals(type)){
            return baiduQianFanChatService;
        }else {
            return null;
        }

    }
}

