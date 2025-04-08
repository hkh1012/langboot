package com.hkh.user.controller;

import com.hkh.common.service.DemandService;
import com.hkh.common.service.SysUserService;
import com.hkh.domain.ResultData;
import com.hkh.domain.constant.SysConstants;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.AgentDemandProposeRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping(value = "/demand/propose")
    public ResultData demandPropose(HttpServletRequest httpServletRequest, AgentDemandProposeRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        demandService.propose(sysUser,request);
        return ResultData.success("保存成功");
    }


}
