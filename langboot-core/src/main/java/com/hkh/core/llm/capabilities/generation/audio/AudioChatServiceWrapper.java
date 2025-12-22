package com.hkh.core.llm.capabilities.generation.audio;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class AudioChatServiceWrapper implements AudioChatService{

    private final AudioChatServiceFactory audioChatServiceFactory;

    @Override
    public String audioToText(File audio, String prompt) {
        AudioChatService audioChatService = audioChatServiceFactory.getAudioChatService();
        return audioChatService.audioToText(audio,prompt);
    }


    @Override
    public InputStream createSpeech(String content, String voiceType) {
        AudioChatService audioChatService = audioChatServiceFactory.getAudioChatService();
        return audioChatService.createSpeech(content, voiceType);
    }
}
