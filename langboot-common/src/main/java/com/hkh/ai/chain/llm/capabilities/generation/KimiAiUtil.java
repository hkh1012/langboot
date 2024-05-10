package com.hkh.ai.chain.llm.capabilities.generation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Kimi 工具类
 * @author huangkh
 */
@Component
@Slf4j
public class KimiAiUtil {

    @Value("${chain.llm.kimi.model}")
    private String completionModel;

    @Value("${kimi.ai.token}")
    private String appKey;

    public String getCompletionModel(){
        return this.completionModel;
    }

    public String getAppKey(){
        return this.appKey;
    }

}
