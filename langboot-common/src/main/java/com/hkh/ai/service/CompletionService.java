package com.hkh.ai.service;

import com.hkh.ai.agent.prompt.demand.functionObj.DemandFuncObj;
import com.hkh.ai.chain.llm.capabilities.generation.function.ChatFunctionObject;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;

import java.util.List;

public interface CompletionService {
    String summary(SysUser sysUser, CompletionSummaryRequest request);

    String keyword(SysUser sysUser, CompletionKeywordRequest request);

    String translate(SysUser sysUser, CompletionTranslateRequest request);

    String classic(SysUser sysUser, CompletionClassicRequest request);

    String security(SysUser sysUser, CompletionSecurityRequest request);

    String function(SysUser sysUser, String content, List<ChatFunctionObject> functionObjectList);

    String functionWeather(SysUser sysUser, CompletionFunctionWeatherRequest request);

}
