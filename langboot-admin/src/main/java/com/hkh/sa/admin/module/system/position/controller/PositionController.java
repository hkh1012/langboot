package com.hkh.sa.admin.module.system.position.controller;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.position.PositionAddForm;
import com.hkh.domain.form.position.PositionQueryForm;
import com.hkh.domain.form.position.PositionUpdateForm;
import com.hkh.domain.vo.position.PositionVO;
import com.hkh.sa.admin.module.system.position.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职务表 Controller
 *
 * @Author kaiyun
 * @Date 2024-06-23 23:31:38
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */

@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_POSITION)
public class PositionController {

    @Resource
    private PositionService positionService;

    @Operation(summary = "分页查询")
    @PostMapping("/position/queryPage")
    public ResponseDTO<PageResult<PositionVO>> queryPage(@RequestBody @Valid PositionQueryForm queryForm) {
        return ResponseDTO.ok(positionService.queryPage(queryForm));
    }

    @Operation(summary = "添加")
    @PostMapping("/position/add")
    public ResponseDTO<String> add(@RequestBody @Valid PositionAddForm addForm) {
        return positionService.add(addForm);
    }

    @Operation(summary = "更新")
    @PostMapping("/position/update")
    public ResponseDTO<String> update(@RequestBody @Valid PositionUpdateForm updateForm) {
        return positionService.update(updateForm);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/position/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return positionService.batchDelete(idList);
    }

    @Operation(summary = "单个删除")
    @GetMapping("/position/delete/{positionId}")
    public ResponseDTO<String> batchDelete(@PathVariable("positionId") Long positionId) {
        return positionService.delete(positionId);
    }


    @Operation(summary = "不分页查询")
    @GetMapping("/position/queryList")
    public ResponseDTO<List<PositionVO>> queryList() {
        return ResponseDTO.ok(positionService.queryList());
    }
}
