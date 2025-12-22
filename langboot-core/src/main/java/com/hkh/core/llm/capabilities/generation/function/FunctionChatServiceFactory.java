package com.hkh.core.llm.capabilities.generation.function;

import com.hkh.core.llm.capabilities.generation.function.baidu.BaiduAiFunctionChatService;
import com.hkh.core.llm.capabilities.generation.function.openai.OpenAiFunctionChatService;
import com.hkh.core.llm.capabilities.generation.function.zhipu.ZhipuAiFunctionChatService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FunctionChatServiceFactory {

    private final OpenAiFunctionChatService openAiFunctionChatService;
    private final BaiduAiFunctionChatService baiduAiFunctionChatService;
    private final ZhipuAiFunctionChatService zhipuAiFunctionChatService;
    private final CapabilityConfigService capabilityConfigService;

    public FunctionChatServiceFactory(OpenAiFunctionChatService openAiFunctionChatService,
                                      BaiduAiFunctionChatService baiduAiFunctionChatService,
                                      ZhipuAiFunctionChatService zhipuAiFunctionChatService, CapabilityConfigService capabilityConfigService) {
        this.openAiFunctionChatService = openAiFunctionChatService;
        this.baiduAiFunctionChatService = baiduAiFunctionChatService;
        this.zhipuAiFunctionChatService = zhipuAiFunctionChatService;
        this.capabilityConfigService = capabilityConfigService;
    }

    public FunctionChatService getFunctionChatService(){
        CapabilityConfigEntity config = capabilityConfigService.getDefaultConfig();
        if (config != null && StringUtils.isNotBlank(config.getFuncModel())){
            String type = config.getFuncModel();
            if("openai".equals(type)){
                return openAiFunctionChatService;
            }else if ("baidu".equals(type)){
                return baiduAiFunctionChatService;
            }else if ("zhipu".equals(type)){
                return zhipuAiFunctionChatService;
            }
        }
        return null;
    }
}

