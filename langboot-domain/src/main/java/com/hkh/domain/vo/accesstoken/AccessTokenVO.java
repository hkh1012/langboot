package com.hkh.domain.vo.accesstoken;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 第三方接口访问token 列表VO
 *
 * @Author huangkh
 * @Date 2025-06-27 09:39:20
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
public class AccessTokenVO {


    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "应用")
    private String app;

    @Schema(description = "token值")
    private String token;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "过期时间")
    private LocalDateTime expiredTime;

    @Schema(description = "创建人")
    private String createBy;

}
