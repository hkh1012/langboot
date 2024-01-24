package com.hkh.ai.chain.llm.capabilities.generation.audio;

import com.hkh.ai.chain.llm.capabilities.generation.audio.openai.OpenAiAudioChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.chatglm2.Chatglm2TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.openai.OpenAiTextChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudioChatServiceFactory {

    @Value("${chain.llm.audio.type}")
    private String type;

    private final OpenAiAudioChatService openAiAudioChatService;

    public AudioChatServiceFactory(OpenAiAudioChatService openAiAudioChatService) {
        this.openAiAudioChatService = openAiAudioChatService;
    }

    public AudioChatService getAudioChatService(){
        if("openai".equals(type)){
            return openAiAudioChatService;
        }else {
            return null;
        }
    }
}

