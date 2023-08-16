package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;
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

    /**
     * 摘要
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/summary"})
    public ResultData<String> summary(HttpServletRequest httpServletRequest, CompletionSummaryRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.summary(sysUser,request);
        return ResultData.success(result,"成功");
    }

    /**
     * 提取关键词
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/keyword"})
    public ResultData<String> keyword(HttpServletRequest httpServletRequest, CompletionKeywordRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.keyword(sysUser,request);
        return ResultData.success(result,"成功");
    }

    /**
     * 翻译
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/translate"})
    public ResultData<String> translate(HttpServletRequest httpServletRequest, CompletionTranslateRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.translate(sysUser,request);
        return ResultData.success(result,"成功");
    }

    /**
     * 文本分类
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/classic"})
    public ResultData<String> classic(HttpServletRequest httpServletRequest, CompletionClassicRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.classic(sysUser,request);
        return ResultData.success(result,"成功");
    }

    /**
     * 内容安全
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/security"})
    public ResultData<String> security(HttpServletRequest httpServletRequest, CompletionSecurityRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String result = completionService.security(sysUser,request);
        return ResultData.success(result,"成功");
    }
}
