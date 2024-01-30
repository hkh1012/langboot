package com.hkh.ai.chain.llm.capabilities.generation.vision;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Primary
@AllArgsConstructor
public class VisionChatServiceWrapper implements VisionChatService{

    private final VisionChatServiceFactory visionChatServiceFactory;

    @Override
    public String visionCompletion(String content, List<String> imageUrlList) {
        VisionChatService visionChatService = visionChatServiceFactory.getVisionChatService();
        return visionChatService.visionCompletion(content,imageUrlList);
    }
}
