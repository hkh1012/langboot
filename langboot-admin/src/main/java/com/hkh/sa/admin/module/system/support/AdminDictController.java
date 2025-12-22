package com.hkh.sa.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.domain.form.dict.*;
import com.hkh.domain.vo.dict.DictDataVO;
import com.hkh.domain.vo.dict.DictVO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.module.support.dict.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典 Controller
 *
 * @Author 1024创新实验室-主任-卓大
 * @Date 2025-03-25 22:25:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.DICT)
@RestController
public class AdminDictController extends SupportBaseController {

    @Resource
    private DictService dictService;

    // -------------------  获取全部数据 -------------------

    @Operation(summary = "获取全部数据（供前端缓存使用）")
    @GetMapping("/dict/getAllDictData")
    public ResponseDTO<List<DictDataVO>> getAll() {
        return ResponseDTO.ok(dictService.getAll());
    }

    @Operation(summary = "获取所有字典code")
    @GetMapping("/dict/getAllDict")
    public ResponseDTO<List<DictVO>> getAllDict() {
        return ResponseDTO.ok(dictService.getAllDict());
    }

    // -------------------  字典 -------------------

    @Operation(summary = "分页查询")
    @PostMapping("/dict/queryPage")
    @SaCheckPermission("support:dict:query")
    public ResponseDTO<PageResult<DictVO>> queryPage(@RequestBody @Valid DictQueryForm queryForm) {
        return ResponseDTO.ok(dictService.queryPage(queryForm));
    }

    @Operation(summary = "添加")
    @PostMapping("/dict/add")
    @SaCheckPermission("support:dict:add")
    public ResponseDTO<String> add(@RequestBody @Valid DictAddForm addForm) {
        return dictService.add(addForm);
    }

    @Operation(summary = "更新")
    @PostMapping("/dict/update")
    @SaCheckPermission("support:dict:update")
    public ResponseDTO<String> update(@RequestBody @Valid DictUpdateForm updateForm) {
        return dictService.update(updateForm);
    }

    @Operation(summary = "启用/禁用")
    @GetMapping("/dict/updateDisabled/{dictId}")
    @SaCheckPermission("support:dict:updateDisabled")
    public ResponseDTO<String> updateDisabled(@PathVariable("dictId") Long dictId) {
        return dictService.updateDisabled(dictId);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/dict/batchDelete")
    @SaCheckPermission("support:dict:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return dictService.batchDelete(idList);
    }

    @Operation(summary = "单个删除")
    @GetMapping("/dict/delete/{dictId}")
    @SaCheckPermission("support:dict:delete")
    public ResponseDTO<String> delete(@PathVariable("dictId") Long dictId) {
        return dictService.delete(dictId);
    }

    // -------------------  字典数据 -------------------

    @Operation(summary = "字典数据 分页查询")
    @GetMapping("/dict/dictData/queryDictData/{dictId}")
    @SaCheckPermission("support:dictData:query")
    public ResponseDTO<List<DictDataVO>> queryDictData(@PathVariable("dictId") Long dictId) {
        return ResponseDTO.ok(dictService.queryDictData(dictId));
    }

    @Operation(summary = "字典数据 启用/禁用")
    @GetMapping("/dict/dictData/updateDisabled/{dictDataId}")
    @SaCheckPermission("support:dictData:updateDisabled")
    public ResponseDTO<String> updateDictDataDisabled(@PathVariable("dictDataId") Long dictDataId) {
        return dictService.updateDictDataDisabled(dictDataId);
    }

    @Operation(summary = "字典数据 添加")
    @PostMapping("/dict/dictData/add")
    @SaCheckPermission("support:dictData:add")
    public ResponseDTO<String> addDictData(@RequestBody @Valid DictDataAddForm addForm) {
        return dictService.addDictData(addForm);
    }

    @Operation(summary = "字典数据 更新")
    @PostMapping("/dict/dictData/update")
    @SaCheckPermission("support:dictData:update")
    public ResponseDTO<String> updateDictData(@RequestBody @Valid DictDataUpdateForm updateForm) {
        return dictService.updateDictData(updateForm);
    }

    @Operation(summary = "字典数据 批量删除")
    @PostMapping("/dict/dictData/batchDelete")
    @SaCheckPermission("support:dictData:delete")
    public ResponseDTO<String> batchDeleteDictData(@RequestBody ValidateList<Long> idList) {
        return dictService.batchDeleteDictData(idList);
    }

    @Operation(summary = "字典数据 单个删除")
    @GetMapping("/dict/dictData/delete/{dictDataId}")
    @SaCheckPermission("support:dictData:delete")
    public ResponseDTO<String> deleteDictData(@PathVariable("dictDataId") Long dictDataId) {
        return dictService.deleteDictData(dictDataId);
    }

}
