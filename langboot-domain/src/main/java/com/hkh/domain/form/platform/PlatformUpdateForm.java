package com.hkh.domain.form.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 平台 更新表单
 *
 * @Author huangkh
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
public class PlatformUpdateForm {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键ID 不能为空")
    private Integer id;

    @Schema(description = "平台代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "平台代码 不能为空")
    private String code;

    @Schema(description = "平台名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "平台名称 不能为空")
    private String name;

    @Schema(description = "秘钥keys", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "秘钥keys 不能为空")
    private String keys;

}