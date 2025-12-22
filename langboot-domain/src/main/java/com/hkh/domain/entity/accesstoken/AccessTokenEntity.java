package com.hkh.domain.entity.accesstoken;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 第三方接口访问token 实体类
 *
 * @Author huangkh
 * @Date 2025-06-27 09:39:20
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@TableName("access_token")
public class AccessTokenEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 应用
     */
    private String app;

    /**
     * token值
     */
    private String token;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expiredTime;

    /**
     * 创建人
     */
    private String createBy;

}
