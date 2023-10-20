package com.hkh.ai.agent.core;


import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.agent.core.function.AiFunctionType;
import com.hkh.ai.agent.core.function.FunctionType;
import com.hkh.ai.agent.core.function.ToolFunctionType;
import com.hkh.ai.agent.prompt.function.FunctionTypePrompt;
import com.hkh.ai.agent.prompt.function.functionObj.FunctionTypeFuncObj;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.DemandStep;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.CompletionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Function;

/**
 * 能力工厂：根据任务获取不同能力
 * @author huangkh
 */
@Component
@AllArgsConstructor
@Slf4j
public class FunctionFactory {

    private final CompletionService completionService;

    public Function getFunction(DemandStep demandStep){
        FunctionType functionType = getFunctionType(demandStep.getRole(),demandStep.getStepName(),demandStep.getDescription());
        switch (functionType){
            case AI:
                AiFunctionType aiFunctionType = getAiFunctionType(demandStep.getRole(),demandStep.getStepName(),demandStep.getDescription());

                break;
            case TOOL:
                ToolFunctionType toolFunctionType = getToolFunctionType(demandStep.getRole(),demandStep.getStepName(),demandStep.getDescription());

                break;
            default:
                System.out.println("DEFAULT");
                break;
        }


        return null;
    }

    private ToolFunctionType getToolFunctionType(String role, String stepName, String description) {
        return null;
    }

    private AiFunctionType getAiFunctionType(String role, String stepName, String description) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);

        return null;
    }

    private FunctionType getFunctionType(String role, String stepName, String description) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        String prompt = FunctionTypePrompt.prompt(role,stepName,description);
        String resultJsonStr = completionService.function(sysUser, prompt,"function_type","get the type of function required to completion task", FunctionTypeFuncObj.class);
        FunctionTypeFuncObj functionTypeFuncObj = JSONObject.parseObject(resultJsonStr,FunctionTypeFuncObj.class);
        log.info("[AGENT]完成步骤{}需要的能力类型为{}",stepName,functionTypeFuncObj.getFunctionType().getType());
        return functionTypeFuncObj.getFunctionType();
    }


}
