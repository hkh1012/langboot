package com.hkh.domain.enums;


import com.hkh.domain.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 消息类型
 *
 * @author luoyi
 * @date 2024/06/22 20:20
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum implements BaseEnum {

    MAIL(1, "站内信"),

    ORDER(2, "订单"),
    ;

    private final Integer value;

    private final String desc;
}
