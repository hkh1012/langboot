package com.hkh.sa.admin.module.system.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.role.RoleDataScopeUpdateForm;
import com.hkh.domain.vo.role.RoleDataScopeVO;
import com.hkh.sa.admin.module.system.role.service.RoleDataScopeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色的数据权限配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-02-26 22:09:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_ROLE_DATA_SCOPE)
public class RoleDataScopeController {

    @Resource
    private RoleDataScopeService roleDataScopeService;

    @Operation(summary = "获取某角色所设置的数据范围")
    @GetMapping("/role/dataScope/getRoleDataScopeList/{roleId}")
    public ResponseDTO<List<RoleDataScopeVO>> dataScopeListByRole(@PathVariable("roleId") Long roleId) {
        return roleDataScopeService.getRoleDataScopeList(roleId);
    }

    @Operation(summary = "批量设置某角色数据范围")
    @PostMapping("/role/dataScope/updateRoleDataScopeList")
    @SaCheckPermission("system:role:dataScope:update")
    public ResponseDTO<String> updateRoleDataScopeList(@RequestBody @Valid RoleDataScopeUpdateForm roleDataScopeUpdateForm) {
        return roleDataScopeService.updateRoleDataScopeList(roleDataScopeUpdateForm);
    }


}
