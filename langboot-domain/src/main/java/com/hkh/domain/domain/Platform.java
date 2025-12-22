package com.hkh.domain.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 平台
 * @TableName platform
 */
@TableName(value ="platform")
@Data
public class Platform implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台代码：包括openai,wenxin,zhipu,kimi,gemini,tongyi,xunfei,claude,local
     */
    @TableField(value = "code")
    private String code;

    /**
     * 平台名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * appKey加appSecret（以两个下划线__分隔）或者api token
     */
    @TableField(value = "token")
    private String token;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}