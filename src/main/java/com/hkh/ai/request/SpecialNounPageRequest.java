package com.hkh.ai.request;

import com.hkh.ai.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "专有名词分页查询参数",description="专有名词分页查询参数")
@Data
public class SpecialNounPageRequest extends PageRequest {

    @Schema(title = "搜索内容",description = "搜索内容",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String searchContent = "";
}
