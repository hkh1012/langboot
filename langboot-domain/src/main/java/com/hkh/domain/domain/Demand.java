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