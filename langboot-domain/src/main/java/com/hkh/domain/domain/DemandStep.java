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
 * 需求步骤
 * @TableName demand_step
 */
@TableName(value ="demand_step")
@Data
public class DemandStep implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 需求ID
     */
    @TableField(value = "did")
    private String did;

    /**
     * 所属领域ID
     */
    @TableField(value = "fid")
    private String fid;

    /**
     * 步骤名称
     */
    @TableField(value = "step_name")
    private String stepName;

    /**
     * 步骤描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 负责人角色
     */
    @TableField(value = "role")
    private String role;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

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