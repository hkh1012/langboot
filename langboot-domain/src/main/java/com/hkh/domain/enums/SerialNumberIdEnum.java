package com.hkh.domain.enums;

import com.hkh.domain.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 单据序列号 枚举
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@AllArgsConstructor
@Getter
public enum SerialNumberIdEnum implements BaseEnum {

    ORDER(1, "订单id"),

    CONTRACT(2, "合同id"),

    ;

    private final Integer serialNumberId;

    private final String desc;

    @Override
    public Integer getValue() {
        return serialNumberId;
    }

    @Override
    public String toString() {
        return "SerialNumberIdEnum{" +
                "serialNumberId=" + serialNumberId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
