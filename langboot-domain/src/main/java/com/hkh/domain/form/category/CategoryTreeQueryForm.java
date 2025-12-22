package com.hkh.domain.form.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 类目 层级树查询
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021/08/05 21:26:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class CategoryTreeQueryForm {

    private Integer categoryType;

    @Schema(description = "父级类目id|可选")
    private Long parentId;
}
