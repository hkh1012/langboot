package com.hkh.ai.chain.llm.capabilities.generation.audio;

import com.hkh.ai.chain.llm.capabilities.generation.audio.openai.OpenAiAudioChatService;
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

