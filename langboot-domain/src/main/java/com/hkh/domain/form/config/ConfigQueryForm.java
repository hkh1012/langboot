package com.hkh.domain.form.config;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 分页查询 系统配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-14 20:46:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class ConfigQueryForm extends PageParam {

    @Schema(description = "参数KEY")
    @Length(max = 50, message = "参数Key最多50字符")
    private String configKey;
}
