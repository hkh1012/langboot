package com.hkh.sa.base.module.support.feedback.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hkh.sa.base.common.json.deserializer.FileKeyVoDeserializer;
import com.hkh.sa.base.common.json.serializer.FileKeyVoSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 意见反馈 返回对象
 *
 * @Author 1024创新实验室: 开云
 * @Date 2022-08-11 20:48:09
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class FeedbackVO {

    @Schema(description = "主键")
    private Long feedbackId;

    @Schema(description = "反馈内容")
    private String feedbackContent;

    @Schema(description = "反馈图片")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String feedbackAttachment;

    @Schema(description = "创建人id")
    private Long userId;

    @Schema(description = "创建人姓名")
    private String userName;

    private Integer userType;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}