package com.hkh.sa.admin.module.system.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.RequestUrlVO;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.menu.MenuAddForm;
import com.hkh.domain.form.menu.MenuUpdateForm;
import com.hkh.domain.vo.menu.MenuTreeVO;
import com.hkh.domain.vo.menu.MenuVO;
import com.hkh.sa.admin.module.system.menu.service.MenuService;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_MENU)
public class MenuController {

    @Resource
    private MenuService menuService;

    @Operation(summary = "添加菜单")
    @PostMapping("/menu/add")
    @SaCheckPermission("system:menu:add")
    public ResponseDTO<String> addMenu(@RequestBody @Valid MenuAddForm menuAddForm) {
        menuAddForm.setCreateUserId(SmartRequestUtil.getRequestUserId());
        return menuService.addMenu(menuAddForm);
    }

    @Operation(summary = "更新菜单")
    @PostMapping("/menu/update")
    @SaCheckPermission("system:menu:update")
    public ResponseDTO<String> updateMenu(@RequestBody @Valid MenuUpdateForm menuUpdateForm) {
        menuUpdateForm.setUpdateUserId(SmartRequestUtil.getRequestUserId());
        return menuService.updateMenu(menuUpdateForm);
    }

    @Operation(summary = "批量删除菜单")
    @GetMapping("/menu/batchDelete")
    @SaCheckPermission("system:menu:batchDelete")
    public ResponseDTO<String> batchDeleteMenu(@RequestParam("menuIdList") List<Long> menuIdList) {
        return menuService.batchDeleteMenu(menuIdList, SmartRequestUtil.getRequestUserId());
    }

    @Operation(summary = "查询菜单列表")
    @GetMapping("/menu/query")
    public ResponseDTO<List<MenuVO>> queryMenuList() {
        return ResponseDTO.ok(menuService.queryMenuList(null));
    }

    @Operation(summary = "查询菜单详情")
    @GetMapping("/menu/detail/{menuId}")
    public ResponseDTO<MenuVO> getMenuDetail(@PathVariable("menuId") Long menuId) {
        return menuService.getMenuDetail(menuId);
    }

    @Operation(summary = "查询菜单树")
    @GetMapping("/menu/tree")
    public ResponseDTO<List<MenuTreeVO>> queryMenuTree(@RequestParam("onlyMenu") Boolean onlyMenu) {
        return menuService.queryMenuTree(onlyMenu);
    }

    @Operation(summary = "获取所有请求路径")
    @GetMapping("/menu/auth/url")
    public ResponseDTO<List<RequestUrlVO>> getAuthUrl() {
        return menuService.getAuthUrl();
    }
}
