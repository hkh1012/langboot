package com.hkh.core.llm.capabilities.generation.text;

import com.hkh.core.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import com.hkh.core.llm.capabilities.generation.text.kimi.KimiTextChatService;
import com.hkh.core.llm.capabilities.generation.text.openai.OpenAiTextChatService;
import com.hkh.core.llm.capabilities.generation.text.qwen.QwenTextChatService;
import com.hkh.core.llm.capabilities.generation.text.zhipu.ZhipuTextChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextChatServiceFactory {

    private final CapabilityConfigService capabilityConfigService;
    private final OpenAiTextChatService openAiTextChatService;
    private final BaiduQianFanTextChatService baiduQianFanTextChatService;
    private final ZhipuTextChatService zhipuTextChatService;
    private final KimiTextChatService kimiTextChatService;
    private final QwenTextChatService qwenTextChatService;

    public TextChatServiceFactory(
            CapabilityConfigService capabilityConfigService, OpenAiTextChatService openAiTextChatService,
            BaiduQianFanTextChatService baiduQianFanTextChatService,
            ZhipuTextChatService zhipuTextChatService,
            KimiTextChatService kimiTextChatService,
            QwenTextChatService qwenTextChatService) {
        this.capabilityConfigService = capabilityConfigService;
        this.openAiTextChatService = openAiTextChatService;
        this.baiduQianFanTextChatService = baiduQianFanTextChatService;
        this.zhipuTextChatService = zhipuTextChatService;
        this.kimiTextChatService = kimiTextChatService;
        this.qwenTextChatService = qwenTextChatService;
    }

    public TextChatService getTextChatService(){
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getJsonModel())) {
            String type = config.getTextModel();
            if("openai".equals(type)){
                return openAiTextChatService;
            }else if("baidu".equals(type)){
                return baiduQianFanTextChatService;
            }else if("zhipu".equals(type)){
                return zhipuTextChatService;
            }else if("kimi".equals(type)){
                return kimiTextChatService;
            }else if ("qwen".equals(type)){
                return qwenTextChatService;
            }
        }
        return null;
    }
}

