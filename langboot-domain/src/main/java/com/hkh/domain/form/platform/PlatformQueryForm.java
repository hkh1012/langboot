package com.hkh.domain.form.platform;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台 分页查询表单
 *
 * @Author huangkh
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformQueryForm extends PageParam {

    @Schema(description = "平台名称")
    private String platform;

}
