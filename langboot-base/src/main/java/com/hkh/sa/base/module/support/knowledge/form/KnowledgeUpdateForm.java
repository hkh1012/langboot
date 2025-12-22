package com.hkh.sa.base.module.support.knowledge.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hkh.sa.base.common.json.deserializer.DictDataDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 知识库 更新表单
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
public class KnowledgeUpdateForm {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键ID 不能为空")
    private Integer id;

    @Schema(description = "知识库编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "知识库编号 不能为空")
    private String knowledgeNo;

    @Schema(description = "知识库名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "知识库名称 不能为空")
    private String knowledgeName;

    @Schema(description = "封面图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "封面图片地址 不能为空")
    private String picUrl;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态：0停用，1在用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态：0停用，1在用 不能为空")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private Integer status;

}