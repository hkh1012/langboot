package com.hkh.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 代理领域
 * @TableName agent_field
 */
@TableName(value ="agent_field")
@Data
public class AgentField implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 领域ID
     */
    @TableField(value = "fid")
    private String fid;

    /**
     * 领域名称
     */
    @TableField(value = "field_name")
    private String fieldName;

    /**
     * 领域描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否生效：0无效1有效
     */
    @TableField(value = "status")
    private Boolean status;

    /**
     * 
     */
    @TableField(value = "create_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "create_by")
    private String createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}