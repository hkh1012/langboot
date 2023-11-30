package com.hkh.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 示例库附件
 * @TableName example_attach
 */
@TableName(value ="example_attach")
@Data
public class ExampleAttach implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 知识库ID
     */
    @TableField(value = "kid")
    private String kid;

    /**
     * 文档ID
     */
    @TableField(value = "doc_id")
    private String docId;

    /**
     * 文档名称
     */
    @TableField(value = "doc_name")
    private String docName;

    /**
     * 文档类型
     */
    @TableField(value = "doc_type")
    private String docType;

    /**
     * 文档内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "create_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "create_by")
    private String createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}