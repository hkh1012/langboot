package com.hkh.ai.chain.llm.capabilities.generation.vision;

import com.hkh.ai.chain.llm.capabilities.generation.vision.openai.OpenAiVisionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.vision.zhipu.ZhipuVisionChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VisionChatServiceFactory {

    @Value("${chain.llm.vision.type}")
    private String type;

    private final OpenAiVisionChatService openAiVisionChatService;
    private final ZhipuVisionChatService zhipuVisionChatService;

    public VisionChatServiceFactory(OpenAiVisionChatService openAiVisionChatService, ZhipuVisionChatService zhipuVisionChatService) {
        this.openAiVisionChatService = openAiVisionChatService;
        this.zhipuVisionChatService = zhipuVisionChatService;
    }

    public VisionChatService getVisionChatService(){
        if("openai".equals(type)){
            return openAiVisionChatService;
        }else if("zhipu".equals(type)){
            return zhipuVisionChatService;
        }else {
            return null;
        }
    }
}
