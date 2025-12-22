package com.hkh.core.llm.capabilities.generation.image;

import com.hkh.core.llm.capabilities.generation.image.baidu.BaiduImageChatService;
import com.hkh.core.llm.capabilities.generation.image.openai.OpenAiImageChatService;
import com.hkh.core.llm.capabilities.generation.image.zhipu.ZhipuImageChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageChatServiceFactory {

    private final OpenAiImageChatService openAiImageChatService;
    private final ZhipuImageChatService zhipuImageChatService;
    private final BaiduImageChatService baiduImageChatService;
    private final CapabilityConfigService capabilityConfigService;

    public ImageChatServiceFactory(OpenAiImageChatService openAiImageChatService,
                                   ZhipuImageChatService zhipuImageChatService,
                                   BaiduImageChatService baiduImageChatService,
                                   CapabilityConfigService capabilityConfigService) {
        this.openAiImageChatService = openAiImageChatService;
        this.zhipuImageChatService = zhipuImageChatService;
        this.baiduImageChatService = baiduImageChatService;
        this.capabilityConfigService = capabilityConfigService;
    }

    public ImageChatService getImageChatService(){
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getImageModel())){
            String type = config.getImageModel();
            if("openai".equals(type)){
                return openAiImageChatService;
            } else if ("baidu".equals(type)) {
                return baiduImageChatService;
            }else if ("zhipu".equals(type)) {
                return zhipuImageChatService;
            }
        }
        return null;
    }
}
