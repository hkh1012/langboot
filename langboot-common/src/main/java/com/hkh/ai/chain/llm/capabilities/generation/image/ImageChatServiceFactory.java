package com.hkh.ai.chain.llm.capabilities.generation.image;

import com.hkh.ai.chain.llm.capabilities.generation.image.baidu.BaiduImageChatService;
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
    private final BaiduImageChatService baiduImageChatService;

    public ImageChatServiceFactory(OpenAiImageChatService openAiImageChatService, ZhipuImageChatService zhipuImageChatService, BaiduImageChatService baiduImageChatService) {
        this.openAiImageChatService = openAiImageChatService;
        this.zhipuImageChatService = zhipuImageChatService;
        this.baiduImageChatService = baiduImageChatService;
    }

    public ImageChatService getImageChatService(){
        if("openai".equals(type)){
            return openAiImageChatService;
        } else if ("baidu".equals(type)) {
            return baiduImageChatService;
        }else if ("zhipu".equals(type)) {
            return zhipuImageChatService;
        } else {
            return null;
        }
    }
}
