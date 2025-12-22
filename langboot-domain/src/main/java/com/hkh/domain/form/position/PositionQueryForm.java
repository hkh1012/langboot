package com.hkh.domain.form.position;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 职务表 分页查询表单
 *
 * @Author kaiyun
 * @Date 2024-06-23 23:31:38
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */

@Data
public class PositionQueryForm extends PageParam {

    @Schema(description = "关键字查询")
    private String keywords;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}