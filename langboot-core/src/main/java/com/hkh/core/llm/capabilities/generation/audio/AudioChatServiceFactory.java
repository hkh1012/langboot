package com.hkh.core.llm.capabilities.generation.audio;

import com.hkh.core.llm.capabilities.generation.audio.baidu.BaiduAudioChatService;
import com.hkh.core.llm.capabilities.generation.audio.openai.OpenAiAudioChatService;
import com.hkh.core.llm.capabilities.generation.audio.tongyi.TongYiAudioChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudioChatServiceFactory {

    private final OpenAiAudioChatService openAiAudioChatService;
    private final BaiduAudioChatService baiduAudioChatService;
    private final TongYiAudioChatService tongyiAudioChatService;
    private final CapabilityConfigService capabilityConfigService;

    public AudioChatServiceFactory(OpenAiAudioChatService openAiAudioChatService,
                                   BaiduAudioChatService baiduAudioChatService,
                                   TongYiAudioChatService tongyiAudioChatService,
                                   CapabilityConfigService capabilityConfigService) {
        this.openAiAudioChatService = openAiAudioChatService;
        this.baiduAudioChatService = baiduAudioChatService;
        this.tongyiAudioChatService = tongyiAudioChatService;
        this.capabilityConfigService = capabilityConfigService;
    }

    public AudioChatService getAudioChatService(){
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getSpeechModel())){
            String type = config.getSpeechModel();
            if("openai".equals(type)){
                return openAiAudioChatService;
            }else if ("baidu".equals(type)){
                return baiduAudioChatService;
            } else if ("qwen".equals(type)) {
                return tongyiAudioChatService;
            }
        }
        return null;
    }
}

