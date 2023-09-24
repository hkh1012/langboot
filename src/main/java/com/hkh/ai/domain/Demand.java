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
 * 需求
 * @TableName demand
 */
@TableName(value ="demand")
@Data
public class Demand implements Serializable {
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
     * 需求内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 明确的
     */
    @TableField(value = "unambiguous")
    private Boolean unambiguous;

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