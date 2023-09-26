package com.hkh.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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