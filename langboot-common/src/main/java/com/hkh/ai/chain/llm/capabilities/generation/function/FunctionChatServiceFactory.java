package com.hkh.ai.chain.llm.capabilities.generation.function;

import com.hkh.ai.chain.llm.capabilities.generation.function.baidu.BaiduAiFunctionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.function.openai.OpenAiFunctionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.function.zhipu.ZhipuAiFunctionChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FunctionChatServiceFactory {

    @Value("${chain.llm.function.type}")
    private String type;

    private final OpenAiFunctionChatService openAiFunctionChatService;
    private final BaiduAiFunctionChatService baiduAiFunctionChatService;
    private final ZhipuAiFunctionChatService zhipuAiFunctionChatService;

    public FunctionChatServiceFactory(OpenAiFunctionChatService openAiFunctionChatService,
                                      BaiduAiFunctionChatService baiduAiFunctionChatService,
                                      ZhipuAiFunctionChatService zhipuAiFunctionChatService) {
        this.openAiFunctionChatService = openAiFunctionChatService;
        this.baiduAiFunctionChatService = baiduAiFunctionChatService;
        this.zhipuAiFunctionChatService = zhipuAiFunctionChatService;
    }

    public FunctionChatService getFunctionChatService(){
        if("openai".equals(type)){
            return openAiFunctionChatService;
        }else if ("baidu".equals(type)){
            return baiduAiFunctionChatService;
        }else if ("zhipu".equals(type)){
            return zhipuAiFunctionChatService;
        } else {
            return null;
        }

    }
}

