package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AgentDemandProposeRequest;
import com.hkh.ai.request.ChatSessionSaveRequest;
import com.hkh.ai.service.DemandService;
import com.hkh.ai.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Agent控制器
 * @author huangkh
 */
@RestController
@AllArgsConstructor
@RequestMapping("agent")
public class AgentController {

    private final SysUserService sysUserService;

    private final DemandService demandService;

    /**
     * 用户的原始需求提出
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping("/demand/propose")
    public ResultData demandPropose(HttpServletRequest httpServletRequest, @RequestBody @Valid AgentDemandProposeRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
//        demandService.propose(sysUser,request);
        return ResultData.success("保存成功");
    }


}
