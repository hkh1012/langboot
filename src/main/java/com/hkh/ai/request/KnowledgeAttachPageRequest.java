package com.hkh.ai.request;

import com.hkh.ai.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "知识库附件分页查询参数",description="知识库附件分页查询参数")
@Data
public class KnowledgeAttachPageRequest extends PageRequest {

    @Schema(title = "知识库kid",description = "知识库kid",requiredMode = Schema.RequiredMode.REQUIRED)
    private String kid = "";

    @Schema(title = "搜索内容",description = "搜索内容",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String searchContent = "";
}
