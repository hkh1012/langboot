package com.hkh.ai.chain.llm.capabilities.generation.text;

import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class TextChatServiceWrapper implements TextChatService{

    private final TextChatServiceFactory textChatServiceFactory;

    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser) throws IOException {
        TextChatService textChatService = textChatServiceFactory.getTextChatService();
        textChatService.streamChat(request, nearestList, history,sseEmitter,sysUser);
    }

    @Override
    public String blockCompletion(String content) {
        TextChatService textChatService = textChatServiceFactory.getTextChatService();
        return textChatService.blockCompletion(content);
    }
}
