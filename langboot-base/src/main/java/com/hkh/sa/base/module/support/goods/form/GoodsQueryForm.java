package com.hkh.sa.base.module.support.goods.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hkh.domain.common.PageParam;
import com.hkh.domain.enums.base.CheckEnum;
import com.hkh.domain.enums.GoodsStatusEnum;
import com.hkh.sa.base.common.json.deserializer.DictDataDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 商品 分页查询
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class GoodsQueryForm extends PageParam {

    @Schema(description = "商品分类")
    private Integer categoryId;

    @Schema(description = "搜索词")
    @Length(max = 30, message = "搜索词最多30字符")
    private String searchWord;

    @CheckEnum(message = "商品状态错误", value = GoodsStatusEnum.class, required = false)
    private Integer goodsStatus;

    @Schema(description = "产地")
    @JsonDeserialize(using = DictDataDeserializer.class)
    private String place;

    @Schema(description = "上架状态")
    private Boolean shelvesFlag;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}
