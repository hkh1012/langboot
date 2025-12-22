package com.hkh.domain.vo.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 平台 列表VO
 *
 * @Author huangkh
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
public class PlatformVO {


    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "平台代码：包括openai,wenxin,zhipu,kimi,gemini,tongyi,xunfei,claude,local")
    private String code;

    @Schema(description = "平台名称")
    private String name;

    @Schema(description = "keys：apiKey与apiSecret")
    private String keys;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createBy;

}
