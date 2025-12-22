package com.hkh.sa.admin.module.system.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.role.RoleAddForm;
import com.hkh.domain.form.role.RoleUpdateForm;
import com.hkh.domain.vo.role.RoleVO;
import com.hkh.sa.admin.module.system.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-12-14 19:40:28
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_ROLE)
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "添加角色")
    @PostMapping("/role/add")
    @SaCheckPermission("system:role:add")
    public ResponseDTO<String> addRole(@Valid @RequestBody RoleAddForm roleAddForm) {
        return roleService.addRole(roleAddForm);
    }

    @Operation(summary = "删除角色")
    @GetMapping("/role/delete/{roleId}")
    @SaCheckPermission("system:role:delete")
    public ResponseDTO<String> deleteRole(@PathVariable("roleId") Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @Operation(summary = "更新角色")
    @PostMapping("/role/update")
    @SaCheckPermission("system:role:update")
    public ResponseDTO<String> updateRole(@Valid @RequestBody RoleUpdateForm roleUpdateDTO) {
        return roleService.updateRole(roleUpdateDTO);
    }

    @Operation(summary = "获取角色数据")
    @GetMapping("/role/get/{roleId}")
    public ResponseDTO<RoleVO> getRole(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @Operation(summary = "获取所有角色")
    @GetMapping("/role/getAll")
    public ResponseDTO<List<RoleVO>> getAllRole() {
        return roleService.getAllRole();
    }

}
