package com.hkh.domain.form.file;

import com.hkh.domain.common.PageParam;
import com.hkh.domain.enums.FileFolderTypeEnum;
import com.hkh.domain.enums.base.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 文件信息查询
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2019年10月11日 15:34:47
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class FileQueryForm extends PageParam {

    @CheckEnum(value = FileFolderTypeEnum.class, message = "文件夹类型 错误")
    private Integer folderType;

    @Schema(description = "文件名词")
    private String fileName;

    @Schema(description = "文件Key")
    private String fileKey;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "创建人")
    private String creatorName;

    @Schema(description = "创建时间")
    private LocalDate createTimeBegin;

    @Schema(description = "创建时间")
    private LocalDate createTimeEnd;

}
