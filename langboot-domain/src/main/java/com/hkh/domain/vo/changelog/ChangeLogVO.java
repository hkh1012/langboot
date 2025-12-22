package com.hkh.domain.vo.changelog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 系统更新日志 列表VO
 *
 *
 * @Date 2022-09-26 14:53:50
 * @Copyright 1024创新实验室
 */

@Data
public class ChangeLogVO {

    private Long changeLogId;

    @Schema(description = "版本")
    private String updateVersion;

    private Integer type;

    @Schema(description = "发布人")
    private String publishAuthor;

    @Schema(description = "发布日期")
    private LocalDate publicDate;

    @Schema(description = "更新内容")
    private String content;

    @Schema(description = "跳转链接")
    private String link;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}