package com.hkh.ai.chain.llm.capabilities.generation.image;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Slf4j
@AllArgsConstructor
public class ImageChatServiceWrapper implements ImageChatService{

    private final ImageChatServiceFactory imageChatServiceFactory;
    @Override
    public List<String> createImage(String prompt) {
        ImageChatService imageChatService = imageChatServiceFactory.getImageChatService();
        return imageChatService.createImage(prompt);
    }

    @Override
    public List<String> editImage(String content, List<String> httpUrls) {
        ImageChatService imageChatService = imageChatServiceFactory.getImageChatService();
        return imageChatService.editImage(content,httpUrls);
    }
}
