package com.hkh.sa.base.module.support.changelog.controller;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.domain.form.changelog.ChangeLogQueryForm;
import com.hkh.domain.vo.changelog.ChangeLogVO;
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
public class ChangeLogController extends SupportBaseController {

    @Resource
    private ChangeLogService changeLogService;

    @Operation(summary = "分页查询")
    @PostMapping("/changeLog/queryPage")
    public ResponseDTO<PageResult<ChangeLogVO>> queryPage(@RequestBody @Valid ChangeLogQueryForm queryForm) {
        return ResponseDTO.ok(changeLogService.queryPage(queryForm));
    }


    @Operation(summary = "变更内容详情")
    @GetMapping("/changeLog/getDetail/{changeLogId}")
    public ResponseDTO<ChangeLogVO> getDetail(@PathVariable("changeLogId") Long changeLogId) {
        return ResponseDTO.ok(changeLogService.getById(changeLogId));
    }
}