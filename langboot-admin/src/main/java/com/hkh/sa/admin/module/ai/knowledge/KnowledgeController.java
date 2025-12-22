package com.hkh.sa.admin.module.ai.knowledge;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.domain.form.knowledge.KnowledgeQueryForm;
import com.hkh.domain.vo.knowledge.KnowledgeVO;
import com.hkh.sa.base.module.support.knowledge.form.KnowledgeAddForm;
import com.hkh.sa.base.module.support.knowledge.form.KnowledgeUpdateForm;
import com.hkh.sa.base.module.support.knowledge.service.KnowledgeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 知识库 Controller
 *
 *
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@RestController
@Tag(name = "知识库")
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    @Operation(summary = "分页查询")
    @PostMapping("/knowledge/queryPage")
    @SaCheckPermission("knowledge:query")
    public ResponseDTO<PageResult<KnowledgeVO>> queryPage(@RequestBody @Valid KnowledgeQueryForm queryForm) {
        return ResponseDTO.ok(knowledgeService.queryPage(queryForm));
    }

    @Operation(summary = "添加")
    @PostMapping("/knowledge/add")
    @SaCheckPermission("knowledge:add")
    public ResponseDTO<String> add(@RequestBody @Valid KnowledgeAddForm addForm) {
        return knowledgeService.add(addForm);
    }

    @Operation(summary = "更新")
    @PostMapping("/knowledge/update")
    @SaCheckPermission("knowledge:update")
    public ResponseDTO<String> update(@RequestBody @Valid KnowledgeUpdateForm updateForm) {
        return knowledgeService.update(updateForm);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/knowledge/batchDelete")
    @SaCheckPermission("knowledge:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Integer> idList) {
        return knowledgeService.batchDelete(idList);
    }

    @Operation(summary = "单个删除")
    @GetMapping("/knowledge/delete/{id}")
    @SaCheckPermission("knowledge:delete")
    public ResponseDTO<String> batchDelete(@PathVariable("id") Integer id) {
        return knowledgeService.delete(id);
    }
}
