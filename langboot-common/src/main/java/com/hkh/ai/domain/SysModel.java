package com.hkh.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 系统模型
 * @TableName sys_model
 */
@TableName(value ="sys_model")
@Data
public class SysModel implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 模型名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "describe")
    private String describe;

    /**
     * 是否本地模型
     */
    @TableField(value = "local")
    private Boolean local;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 是否免费
     */
    @TableField(value = "free")
    private Boolean free;

    /**
     * 标准token比
     */
    @TableField(value = "std_rate")
    private BigDecimal stdRate;

    /**
     * 角色设定
     */
    @TableField(value = "role_text")
    private String roleText;

    /**
     * 模型默认温度
     */
    @TableField(value = "temperature")
    private BigDecimal temperature;

    /**
     * 默认结果
     */
    @TableField(value = "top_p")
    private Integer topP;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "create_by")
    private String createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}