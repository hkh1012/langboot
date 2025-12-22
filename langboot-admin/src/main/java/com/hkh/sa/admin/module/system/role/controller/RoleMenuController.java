package com.hkh.sa.admin.module.system.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.role.RoleMenuUpdateForm;
import com.hkh.domain.vo.role.RoleMenuTreeVO;
import com.hkh.sa.admin.module.system.role.service.RoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 角色的菜单
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-02-26 21:34:01
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_ROLE_MENU)
public class RoleMenuController {

    @Resource
    private RoleMenuService roleMenuService;

    @Operation(summary = "更新角色权限")
    @PostMapping("/role/menu/updateRoleMenu")
    @SaCheckPermission("system:role:menu:update")
    public ResponseDTO<String> updateRoleMenu(@Valid @RequestBody RoleMenuUpdateForm updateDTO) {
        return roleMenuService.updateRoleMenu(updateDTO);
    }

    @Operation(summary = "获取角色关联菜单权限")
    @GetMapping("/role/menu/getRoleSelectedMenu/{roleId}")
    public ResponseDTO<RoleMenuTreeVO> getRoleSelectedMenu(@PathVariable("roleId") Long roleId) {
        return roleMenuService.getRoleSelectedMenu(roleId);
    }
}
