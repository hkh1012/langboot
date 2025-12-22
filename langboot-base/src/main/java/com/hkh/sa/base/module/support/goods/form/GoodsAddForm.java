package com.hkh.sa.base.module.support.goods.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hkh.domain.enums.base.CheckEnum;
import com.hkh.domain.enums.GoodsStatusEnum;
import com.hkh.sa.base.common.json.deserializer.DictDataDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品 添加表单
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class GoodsAddForm {

    @Schema(description = "商品分类")
    @NotNull(message = "商品分类不能为空")
    private Long categoryId;

    @Schema(description = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;

    @CheckEnum(message = "商品状态错误", value = GoodsStatusEnum.class, required = true)
    private Integer goodsStatus;

    @Schema(description = "产地")
    @NotBlank(message = "产地 不能为空 ")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private String place;

    @Schema(description = "商品价格")
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0", message = "商品价格最低0")
    private BigDecimal price;

    @Schema(description = "上架状态")
    @NotNull(message = "上架状态不能为空")
    private Boolean shelvesFlag;

    @Schema(description = "备注|可选")
    private String remark;
}
