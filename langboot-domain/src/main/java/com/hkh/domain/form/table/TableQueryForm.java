package com.hkh.domain.form.table;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 查询表数据
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-06-30 22:15:38
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class TableQueryForm extends PageParam {

    @Schema(description = "表名关键字")
    private String tableNameKeywords;

}
