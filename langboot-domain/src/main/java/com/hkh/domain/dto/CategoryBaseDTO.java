package com.hkh.domain.dto;

import com.hkh.domain.enums.CategoryTypeEnum;
import com.hkh.domain.enums.base.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 类目 基础属性 DTO 类
 *
 *
 * @date 2021/1/20 16:17
 */
@Data
public class CategoryBaseDTO {

    @Schema(description = "类目名称", required = true)
    @NotBlank(message = "类目名称不能为空")
    @Length(max = 20, message = "类目名称最多20字符")
    private String categoryName;

    @CheckEnum(value = CategoryTypeEnum.class, required = true, message = "分类错误")
    private Integer categoryType;

    @Schema(description = "父级类目id|可选")
    private Long parentId;

    @Schema(description = "排序|可选")
    private Integer sort;

    @Schema(description = "备注|可选")
    @Length(max = 200, message = "备注最多200字符")
    private String remark;

    @Schema(description = "禁用状态")
    @NotNull(message = "禁用状态不能为空")
    private Boolean disabledFlag;
}
