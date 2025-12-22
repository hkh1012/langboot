package com.hkh.sa.admin.module.ai.platform.controller;


import com.hkh.sa.base.module.support.platform.service.PlatformService;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.domain.form.platform.PlatformAddForm;
import com.hkh.domain.form.platform.PlatformQueryForm;
import com.hkh.domain.form.platform.PlatformUpdateForm;
import com.hkh.domain.vo.platform.PlatformVO;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 平台 Controller
 *
 *
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@RestController
@Tag(name = "平台")
@RequestMapping("platform")
public class PlatformController {

    @Resource
    private PlatformService platformService;

    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    @SaCheckPermission("platform:query")
    public ResponseDTO<PageResult<PlatformVO>> queryPage(@RequestBody @Valid PlatformQueryForm queryForm) {
        return ResponseDTO.ok(platformService.queryPage(queryForm));
    }

    @Operation(summary = "添加")
    @PostMapping("/add")
    @SaCheckPermission("platform:add")
    public ResponseDTO<String> add(@RequestBody @Valid PlatformAddForm addForm) {
        return platformService.add(addForm);
    }

    @Operation(summary = "更新")
    @PostMapping("/update")
    @SaCheckPermission("platform:update")
    public ResponseDTO<String> update(@RequestBody @Valid PlatformUpdateForm updateForm) {
        return platformService.update(updateForm);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/batchDelete")
    @SaCheckPermission("platform:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Integer> idList) {
        return platformService.batchDelete(idList);
    }

    @Operation(summary = "单个删除")
    @GetMapping("/delete/{id}")
    @SaCheckPermission("platform:delete")
    public ResponseDTO<String> batchDelete(@PathVariable("id") Integer id) {
        return platformService.delete(id);
    }
}
