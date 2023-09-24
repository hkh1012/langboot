package com.hkh.ai.service;

import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;

public interface CompletionService {
    String summary(SysUser sysUser, CompletionSummaryRequest request);

    String keyword(SysUser sysUser, CompletionKeywordRequest request);

    String translate(SysUser sysUser, CompletionTranslateRequest request);

    String classic(SysUser sysUser, CompletionClassicRequest request);

    String security(SysUser sysUser, CompletionSecurityRequest request);

    String function(SysUser sysUser, CompletionFunctionRequest request);

    String functionWeather(SysUser sysUser, CompletionFunctionWeatherRequest request);
}
