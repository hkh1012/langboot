package com.hkh.ai.common.exception;

/**
 * 参数异常定义
 */
public class ParamException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ParamException(String message) {
        super(message);
    }
}
