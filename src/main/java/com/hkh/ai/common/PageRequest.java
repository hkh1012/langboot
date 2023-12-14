package com.hkh.ai.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页查询参数基类
 * @author huangkh
 */
@Schema(title = "分页查询参数基类",description="用于分页查询参数基类，供其他类继承")
@Data
public class PageRequest {

    @Schema(title = "当前第几页，默认1",description = "当前第几页，默认1",defaultValue = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNum = 1;
    @Schema(title = "每页条数，默认10",description = "每页条数，默认10",defaultValue = "10",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize = 3;

}
