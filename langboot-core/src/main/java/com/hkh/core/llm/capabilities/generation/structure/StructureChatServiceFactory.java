package com.hkh.core.llm.capabilities.generation.structure;

import com.hkh.core.llm.capabilities.generation.structure.baidu.BaiduStructureChatService;
import com.hkh.core.llm.capabilities.generation.structure.openai.OpenAiStructureChatService;
import com.hkh.core.llm.capabilities.generation.structure.tongyi.TongYiStructureChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StructureChatServiceFactory {

    private final CapabilityConfigService capabilityConfigService;
    private final OpenAiStructureChatService openAiStructureChatService;
    private final BaiduStructureChatService baiduStructureChatService;
    private final TongYiStructureChatService tongYiStructureChatService;

    public StructureChatServiceFactory(CapabilityConfigService capabilityConfigService,
                                       OpenAiStructureChatService openAiStructureChatService,
                                       BaiduStructureChatService baiduStructureChatService,
                                       TongYiStructureChatService tongYiStructureChatService) {
        this.capabilityConfigService = capabilityConfigService;
        this.openAiStructureChatService = openAiStructureChatService;
        this.baiduStructureChatService = baiduStructureChatService;
        this.tongYiStructureChatService = tongYiStructureChatService;
    }

    public StructureChatService getStructureChatService() {
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getJsonModel())) {
            String type = config.getJsonModel();
            if ("openai".equals(type)) {
                return openAiStructureChatService;
            }else if("wenxinyiyan".equals(type)) {
                return baiduStructureChatService;
            }else if("qwen".equals(type)) {
                return tongYiStructureChatService;
            }
        }
        return null;
    }
}
