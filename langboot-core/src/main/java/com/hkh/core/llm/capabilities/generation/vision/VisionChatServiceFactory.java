package com.hkh.core.llm.capabilities.generation.vision;

import com.hkh.core.llm.capabilities.generation.vision.openai.OpenAiVisionChatService;
import com.hkh.core.llm.capabilities.generation.vision.zhipu.ZhipuVisionChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VisionChatServiceFactory {

    private final CapabilityConfigService capabilityConfigService;
    private final OpenAiVisionChatService openAiVisionChatService;
    private final ZhipuVisionChatService zhipuVisionChatService;

    public VisionChatServiceFactory(CapabilityConfigService capabilityConfigService,
                                    OpenAiVisionChatService openAiVisionChatService,
                                    ZhipuVisionChatService zhipuVisionChatService) {
        this.capabilityConfigService = capabilityConfigService;
        this.openAiVisionChatService = openAiVisionChatService;
        this.zhipuVisionChatService = zhipuVisionChatService;
    }

    public VisionChatService getVisionChatService(){
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getVisionModel())) {
            String type = config.getVisionModel();
            if("openai".equals(type)){
                return openAiVisionChatService;
            }else if("zhipu".equals(type)){
                return zhipuVisionChatService;
            }
        }
        return null;
    }
}
