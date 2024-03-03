package com.hkh.ai.chain.llm.capabilities.generation.image;

import com.hkh.ai.chain.llm.capabilities.generation.image.openai.OpenAiImageChatService;
import com.hkh.ai.chain.llm.capabilities.generation.image.zhipu.ZhipuImageChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageChatServiceFactory {

    @Value("${chain.llm.image.type}")
    private String type;

    private final OpenAiImageChatService openAiImageChatService;
    private final ZhipuImageChatService zhipuImageChatService;

    public ImageChatServiceFactory(OpenAiImageChatService openAiImageChatService, ZhipuImageChatService zhipuImageChatService) {
        this.openAiImageChatService = openAiImageChatService;
        this.zhipuImageChatService = zhipuImageChatService;
    }

    public ImageChatService getImageChatService(){
        if("openai".equals(type)){
            return openAiImageChatService;
        } else if ("zhipu".equals(type)) {
            return zhipuImageChatService;
        } else {
            return null;
        }
    }
}
