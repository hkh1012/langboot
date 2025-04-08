package com.hkh.common.service;


import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.*;
import com.hkh.core.llm.capabilities.generation.function.ChatFunctionObject;

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
