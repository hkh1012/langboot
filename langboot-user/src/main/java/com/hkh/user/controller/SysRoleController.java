package com.hkh.user.controller;

import com.github.pagehelper.PageInfo;
import com.hkh.common.common.annotation.TokenIgnore;
import com.hkh.common.service.SysRoleService;
import com.hkh.domain.ResultData;
import com.hkh.domain.constant.SysConstants;
import com.hkh.domain.domain.SysRole;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.RolePageRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色功能
 */
@RestController
@AllArgsConstructor
@RequestMapping("role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 音频转文本
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/page"})
    @TokenIgnore
    public ResultData<PageInfo<SysRole>> rolePage(HttpServletRequest httpServletRequest, @RequestBody RolePageRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        PageInfo<SysRole> pageInfo = sysRoleService.pageInfo(request);
        return ResultData.success(pageInfo,"成功");
    }

}
