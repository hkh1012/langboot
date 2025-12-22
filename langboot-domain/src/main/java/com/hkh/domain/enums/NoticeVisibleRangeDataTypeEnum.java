package com.hkh.domain.enums;


import com.hkh.domain.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告、通知 可见范围类型
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Getter
@AllArgsConstructor
public enum NoticeVisibleRangeDataTypeEnum implements BaseEnum {

    /**
     * 员工
     */
    EMPLOYEE(1, "员工"),

    /**
     * 部门
     */
    DEPARTMENT(2, "部门"),

    ;

    private final Integer value;

    private final String desc;
}
