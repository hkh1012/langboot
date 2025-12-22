package com.hkh.core.llm.capabilities.generation.text;

import com.hkh.domain.dto.FluxStreamDto;
import com.hkh.domain.dto.HistoryMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class TextChatServiceWrapper implements TextChatService{

    private final TextChatServiceFactory textChatServiceFactory;

    @Override
    public String blockCompletion(String content) {
        TextChatService textChatService = textChatServiceFactory.getTextChatService();
        return textChatService.blockCompletion(content);
    }

    @Override
    public FluxStreamDto stream(String content, String systemPrompt, List<String> nearestList, List<HistoryMessageDto> historyList) {
        TextChatService textChatService = textChatServiceFactory.getTextChatService();
        return textChatService.stream(content, systemPrompt,nearestList, historyList);
    }
}
