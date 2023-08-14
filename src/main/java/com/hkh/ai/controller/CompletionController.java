package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.CompletionKeywordRequest;
import com.hkh.ai.request.CompletionSummaryRequest;
import com.hkh.ai.request.CompletionTranslateRequest;
import com.hkh.ai.service.CompletionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("completion")
public class CompletionController {

    private final CompletionService completionService;

    @PostMapping(value = {"/summary"})
    public ResultData<String> summary(HttpServletRequest httpServletRequest, CompletionSummaryRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.summary(sysUser,request);
        return ResultData.success(result,"成功");
    }

    @PostMapping(value = {"/keyword"})
    public ResultData<String> keyword(HttpServletRequest httpServletRequest, CompletionKeywordRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.keyword(sysUser,request);
        return ResultData.success(result,"成功");
    }

    @PostMapping(value = {"/translate"})
    public ResultData<String> translate(HttpServletRequest httpServletRequest, CompletionTranslateRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.translate(sysUser,request);
        return ResultData.success(result,"成功");
    }
}
