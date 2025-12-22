package com.hkh.domain.form.knowledge;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库 分页查询表单
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class KnowledgeQueryForm extends PageParam {

    @Schema(description = "关键词搜索")
    private String query;

}
