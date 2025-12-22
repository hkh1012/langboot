package com.hkh.domain.form.file;

import com.hkh.domain.enums.FileFolderTypeEnum;
import com.hkh.domain.enums.base.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * url上传文件
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2019年10月11日 15:34:47
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class FileUrlUploadForm {

    @CheckEnum(value = FileFolderTypeEnum.class, required = true, message = "业务类型错误")
    private Integer folder;

    @Schema(description = "文件url")
    @NotBlank(message = "文件url不能为空")
    private String fileUrl;

}
