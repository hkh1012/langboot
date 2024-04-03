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
 * 媒体文件
 * @TableName media_file
 */
@TableName(value ="media_file")
@Data
public class MediaFile implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对话id
     */
    @TableField(value = "cid")
    private Integer cid;

    /**
     * 媒体文件ID
     */
    @TableField(value = "mfid")
    private String mfid;

    /**
     * 媒体类型：1视频，2音频，3图片,4文档
     */
    @TableField(value = "media_type")
    private Integer mediaType;

    /**
     * 文件后缀
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件大小(单位字节)
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 时长(单位秒)
     */
    @TableField(value = "file_time")
    private Long fileTime;

    /**
     * 文件路径
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * http全路径
     */
    @TableField(value = "http_url")
    private String httpUrl;

    /**
     * 创建时间
     */
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