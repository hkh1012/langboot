package com.hkh.ai.common;

public enum ResultCodeEnum {

    SUCCESS("200", "成功"),

    FAIL("400", "操作失败"),

    AUTH_FAIL("401", "认证失败"),

    URL_ERROR("404", "URL错误"),

    SYS_ERR("500", "系统错误"),
    PARAM_ERROR("50001", "参数校验错误"),

    TOKEN_ERROR("50002", "TOKEN校验异常"),

    REPEAT_SUBMIT("50003", "重读提交"),

    DB_ERR("50004","数据库操作错误"),

    NO_PERMISSION("60001", "无权限");


    private final String code;
    private final String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
