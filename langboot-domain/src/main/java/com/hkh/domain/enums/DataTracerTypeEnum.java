package com.hkh.domain.enums;


import com.hkh.domain.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据业务类型
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-07-23 19:38:52-
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@AllArgsConstructor
@Getter
public enum DataTracerTypeEnum implements BaseEnum {

    /**
     * 商品
     */
    GOODS(1, "商品"),

    /**
     *通知公告
     */
    OA_NOTICE(2, "OA-通知公告"),

    /**
     * 企业信息
     */
    OA_ENTERPRISE(3, "OA-企业信息"),

    ;

    private final Integer value;

    private final String desc;
}
