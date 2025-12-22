package com.hkh.domain.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件信息
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2019年10月11日 15:34:47
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class FileVO {

    @Schema(description = "主键")
    private Long fileId;

    @Schema(description = "存储文件夹类型")
    private Integer folderType;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件大小")
    private Integer fileSize;

    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件uuid
     */
    @Schema(description = "文件uuid")
    private String uuid;

    /**
     * 完整http url
     */
    @Schema(description = "完整http url")
    private String httpUrl;

    /**
     * 音频文件播放时长
     */
    @Schema(description = "音频文件播放时长")
    private Long fileTime;

    @Schema(description = "文件路径")
    private String fileKey;

    @Schema(description = "上传人")
    private Long creatorId;

    @Schema(description = "上传人")
    private String creatorName;

    private Integer creatorUserType;

    @Schema(description = "文件展示url")
    private String fileUrl;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
