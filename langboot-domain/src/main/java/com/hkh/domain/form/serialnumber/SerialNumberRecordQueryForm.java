package com.hkh.domain.form.serialnumber;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 单据序列号 生成记录 查询
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class SerialNumberRecordQueryForm extends PageParam {

    @Schema(description = "单号id")
    @NotNull(message = "单号id不能为空")
    private Integer serialNumberId;
}
