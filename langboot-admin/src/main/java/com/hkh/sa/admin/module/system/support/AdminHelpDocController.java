package com.hkh.sa.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.form.helpdoc.HelpDocCatalogAddForm;
import com.hkh.domain.form.helpdoc.HelpDocCatalogUpdateForm;
import com.hkh.domain.form.helpdoc.HelpDocQueryForm;
import com.hkh.domain.vo.helpdoc.HelpDocVO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.module.support.helpdoc.domain.form.*;
import com.hkh.sa.base.module.support.helpdoc.domain.vo.HelpDocDetailVO;
import com.hkh.sa.base.module.support.helpdoc.service.HelpDocCatalogService;
import com.hkh.sa.base.module.support.helpdoc.service.HelpDocService;
import com.hkh.sa.base.module.support.repeatsubmit.annoation.RepeatSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帮助文档
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-20 23:11:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.HELP_DOC)
@RestController
public class AdminHelpDocController extends SupportBaseController {

    @Resource
    private HelpDocService helpDocService;

    @Resource
    private HelpDocCatalogService helpDocCatalogService;

    // --------------------- 帮助文档 【目录管理】 -------------------------


    @Operation(summary = "帮助文档目录-添加")
    @PostMapping("/helpDoc/helpDocCatalog/add")
    public ResponseDTO<String> addHelpDocCatalog(@RequestBody @Valid HelpDocCatalogAddForm helpDocCatalogAddForm) {
        return helpDocCatalogService.add(helpDocCatalogAddForm);
    }

    @Operation(summary = "帮助文档目录-更新")
    @PostMapping("/helpDoc/helpDocCatalog/update")
    public ResponseDTO<String> updateHelpDocCatalog(@RequestBody @Valid HelpDocCatalogUpdateForm helpDocCatalogUpdateForm) {
        return helpDocCatalogService.update(helpDocCatalogUpdateForm);
    }

    @Operation(summary = "帮助文档目录-删除")
    @GetMapping("/helpDoc/helpDocCatalog/delete/{helpDocCatalogId}")
    public ResponseDTO<String> deleteHelpDocCatalog(@PathVariable("helpDocCatalogId") Long helpDocCatalogId) {
        return helpDocCatalogService.delete(helpDocCatalogId);
    }

    // --------------------- 帮助文档 【管理:增、删、查、改】-------------------------

    @Operation(summary = "【管理】帮助文档-分页查询")
    @PostMapping("/helpDoc/query")
    @SaCheckPermission("support:helpDoc:query")
    public ResponseDTO<PageResult<HelpDocVO>> query(@RequestBody @Valid HelpDocQueryForm queryForm) {
        return ResponseDTO.ok(helpDocService.query(queryForm));
    }

    @Operation(summary = "【管理】帮助文档-获取详情")
    @GetMapping("/helpDoc/getDetail/{helpDocId}")
    @SaCheckPermission("support:helpDoc:add")
    public ResponseDTO<HelpDocDetailVO> getDetail(@PathVariable("helpDocId") Long helpDocId) {
        return ResponseDTO.ok(helpDocService.getDetail(helpDocId));
    }

    @Operation(summary = "【管理】帮助文档-添加")
    @PostMapping("/helpDoc/add")
    @RepeatSubmit
    public ResponseDTO<String> add(@RequestBody @Valid HelpDocAddForm addForm) {
        return helpDocService.add(addForm);
    }

    @Operation(summary = "【管理】帮助文档-更新")
    @PostMapping("/helpDoc/update")
    @RepeatSubmit
    public ResponseDTO<String> update(@RequestBody @Valid HelpDocUpdateForm updateForm) {
        return helpDocService.update(updateForm);
    }

    @Operation(summary = "【管理】帮助文档-删除")
    @GetMapping("/helpDoc/delete/{helpDocId}")
    public ResponseDTO<String> delete(@PathVariable("helpDocId") Long helpDocId) {
        return helpDocService.delete(helpDocId);
    }

    @Operation(summary = "【管理】帮助文档-根据关联id查询")
    @GetMapping("/helpDoc/queryHelpDocByRelationId/{relationId}")
    public ResponseDTO<List<HelpDocVO>> queryHelpDocByRelationId(@PathVariable("relationId") Long relationId) {
        return ResponseDTO.ok(helpDocService.queryHelpDocByRelationId(relationId));
    }

}