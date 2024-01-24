package com.hkh.ai.chain.llm.capabilities.generation.audio;

import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.InputStream;
import java.util.List;

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
    public void audioChat(CustomChatMessage customChatMessage, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser, String mediaId) {
        AudioChatService audioChatService = audioChatServiceFactory.getAudioChatService();
        audioChatService.audioChat(customChatMessage, nearestList, history, sseEmitter, sysUser, mediaId);
    }

    @Override
    public InputStream createSpeech(String content) {
        AudioChatService audioChatService = audioChatServiceFactory.getAudioChatService();
        return audioChatService.createSpeech(content);
    }
}
