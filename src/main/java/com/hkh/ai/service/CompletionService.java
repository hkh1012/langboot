package com.hkh.ai.service;

import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.CompletionClassicRequest;
import com.hkh.ai.request.CompletionKeywordRequest;
import com.hkh.ai.request.CompletionSummaryRequest;
import com.hkh.ai.request.CompletionTranslateRequest;

public interface CompletionService {
    String summary(SysUser sysUser, CompletionSummaryRequest request);

    String keyword(SysUser sysUser, CompletionKeywordRequest request);

    String translate(SysUser sysUser, CompletionTranslateRequest request);

    String classic(SysUser sysUser, CompletionClassicRequest request);
}
