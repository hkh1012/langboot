package com.hkh.ai.config;

import com.hkh.ai.common.ResultCodeEnum;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.exception.AuthException;
import com.hkh.ai.common.exception.Request404Exception;
import com.hkh.ai.common.exception.SessionExpiredException;
import com.hkh.ai.common.exception.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResultData expireExceptionHandler(TokenExpiredException e) {
        log.error("会话过期：",e.getMessage(),e);
        return ResultData.fail(ResultCodeEnum.AUTH_FAIL,e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResultData authExceptionHandler(AuthException e) {
        log.error("认证失败：",e.getMessage(),e);
        return ResultData.fail(ResultCodeEnum.AUTH_FAIL,e.getMessage());
    }

    @ExceptionHandler(Request404Exception.class)
    public ResultData request404ExceptionHandler(Request404Exception e) {
        log.error("404：",e.getMessage(),e);
        return ResultData.fail(ResultCodeEnum.URL_ERROR,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultData exceptionHandler(Exception e) {
        log.error("系统异常：",e.getMessage(),e);
        return ResultData.fail(ResultCodeEnum.SYS_ERR,e.getMessage());
    }
}
