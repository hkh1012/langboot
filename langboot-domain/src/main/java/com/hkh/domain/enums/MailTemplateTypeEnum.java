package com.hkh.domain.enums;

import com.hkh.domain.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 邮件模板类型
 *
 * @Author 1024创新实验室-创始人兼主任:卓大
 * @Date 2024/8/5
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a> ，Since 2012
 */

@Getter
@AllArgsConstructor
public enum MailTemplateTypeEnum implements BaseEnum {

    STRING("string", "字符串替代器"),

    FREEMARKER("freemarker", "freemarker模板引擎");

    private String value;

    private String desc;


}