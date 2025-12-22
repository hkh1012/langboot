package com.hkh.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 平台 实体类
 *
 * @Author huangkh
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@TableName("platform")
public class PlatformEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 平台代码：包括openai,wenxin,zhipu,kimi,gemini,tongyi,xunfei,claude,local
     */
    private String code;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 秘钥keys：apiKey与apiSecret
     */
    private String keys;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

}
