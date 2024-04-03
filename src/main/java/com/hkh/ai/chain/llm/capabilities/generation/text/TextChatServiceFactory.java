package com.hkh.ai.chain.llm.capabilities.generation.text;

import com.hkh.ai.chain.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.chatglm2.Chatglm2TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.kimi.KimiTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.openai.OpenAiTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.zhipu.ZhipuTextChatService;
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
    private final ZhipuTextChatService zhipuTextChatService;
    private final KimiTextChatService kimiTextChatService;

    public TextChatServiceFactory(
            OpenAiTextChatService openAiTextChatService,
            Chatglm2TextChatService chatglm2TextChatService,
            BaiduQianFanTextChatService baiduQianFanTextChatService,
            ZhipuTextChatService zhipuTextChatService,
            KimiTextChatService kimiTextChatService) {
        this.openAiTextChatService = openAiTextChatService;
        this.chatglm2TextChatService = chatglm2TextChatService;
        this.baiduQianFanTextChatService = baiduQianFanTextChatService;
        this.zhipuTextChatService = zhipuTextChatService;
        this.kimiTextChatService = kimiTextChatService;
    }

    public TextChatService getTextChatService(){
        if("openai".equals(type)){
            return openAiTextChatService;
        }else if("chatglm2".equals(type)){
            return chatglm2TextChatService;
        }else if("baidu".equals(type)){
            return baiduQianFanTextChatService;
        }else if("zhipu".equals(type)){
            return zhipuTextChatService;
        }else if("kimi".equals(type)){
            return kimiTextChatService;
        }else {
            return null;
        }

    }
}

