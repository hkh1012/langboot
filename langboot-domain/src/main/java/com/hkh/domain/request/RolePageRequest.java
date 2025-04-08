package com.hkh.domain.request;

import com.hkh.domain.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "角色分页查询参数",description="角色分页查询参数")
@Data
public class RolePageRequest extends PageRequest {

    @Schema(title = "搜索内容",description = "搜索内容",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String searchContent = "";
}
