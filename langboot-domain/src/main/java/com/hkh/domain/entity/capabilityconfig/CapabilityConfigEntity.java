package com.hkh.domain.entity.capabilityconfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模型能力配置
 * @TableName capability_config
 */
@TableName(value ="capability_config")
@Data
public class CapabilityConfigEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文本
     */
    @TableField(value = "text_model")
    private String textModel;

    /**
     * 图像
     */
    @TableField(value = "image_model")
    private String imageModel;

    /**
     * 视觉多模态
     */
    @TableField(value = "vision_model")
    private String visionModel;

    /**
     * 函数
     */
    @TableField(value = "func_model")
    private String funcModel;

    /**
     * 结构化
     */
    @TableField(value = "json_model")
    private String jsonModel;

    /**
     * 语音
     */
    @TableField(value = "speech_model")
    private String speechModel;

    /**
     * 全向
     */
    @TableField(value = "omni_model")
    private String omniModel;

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