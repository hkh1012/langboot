package com.hkh.domain.form.accesstoken;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 第三方接口访问token 分页查询表单
 *
 * @Author huangkh
 * @Date 2025-06-27 09:39:20
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class AccessTokenQueryForm extends PageParam {

    @Schema(description = "应用名称")
    private String app;

    @Schema(description = "过期时间")
    private LocalDate expiredTimeBegin;

    @Schema(description = "过期时间")
    private LocalDate expiredTimeEnd;

}
