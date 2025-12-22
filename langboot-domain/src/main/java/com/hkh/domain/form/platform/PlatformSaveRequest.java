package com.hkh.domain.form.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "平台保存请求参数")
public class PlatformSaveRequest {

    @Schema(description  = "code",title = "code",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "code不能为空")
    private String code;

    @Schema(description  = "name",title = "name",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "平台名称不能为空")
    private String name;

    @Schema(description  = "token",title = "token",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "平台token不能为空")
    private String token;
}
