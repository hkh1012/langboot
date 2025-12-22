package com.hkh.sa.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.domain.form.changelog.ChangeLogAddForm;
import com.hkh.domain.form.changelog.ChangeLogUpdateForm;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.module.support.changelog.service.ChangeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 系统更新日志 Controller
 *
 *
 * @Date 2022-09-26 14:53:50
 * @Copyright 1024创新实验室
 */

@RestController
@Tag(name = SwaggerTagConst.Support.CHANGE_LOG)
public class AdminChangeLogController extends SupportBaseController {

    @Resource
    private ChangeLogService changeLogService;

    @Operation(summary = "添加")
    @PostMapping("/changeLog/add")
    @SaCheckPermission("support:changeLog:add")
    public ResponseDTO<String> add(@RequestBody @Valid ChangeLogAddForm addForm) {
        return changeLogService.add(addForm);
    }

    @Operation(summary = "更新")
    @PostMapping("/changeLog/update")
    @SaCheckPermission("support:changeLog:update")
    public ResponseDTO<String> update(@RequestBody @Valid ChangeLogUpdateForm updateForm) {
        return changeLogService.update(updateForm);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/changeLog/batchDelete")
    @SaCheckPermission("support:changeLog:batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return changeLogService.batchDelete(idList);
    }

    @Operation(summary = "单个删除")
    @GetMapping("/changeLog/delete/{changeLogId}")
    @SaCheckPermission("support:changeLog:delete")
    public ResponseDTO<String> batchDelete(@PathVariable("changeLogId") Long changeLogId) {
        return changeLogService.delete(changeLogId);
    }
}