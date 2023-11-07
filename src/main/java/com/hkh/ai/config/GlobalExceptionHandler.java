package com.hkh.ai.config;

import com.hkh.ai.common.exception.AuthException;
import com.hkh.ai.common.exception.Request404Exception;
import com.hkh.ai.common.exception.SessionExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SessionExpiredException.class)
    public ModelAndView expireExceptionHandler(SessionExpiredException e) {
        log.error("会话过期：",e.getMessage(),e);
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @ExceptionHandler(AuthException.class)
    public ModelAndView authExceptionHandler(AuthException e) {
        log.error("认证失败：",e.getMessage(),e);
        ModelAndView modelAndView = new ModelAndView("error/401");
        return modelAndView;
    }

    @ExceptionHandler(Request404Exception.class)
    public ModelAndView request404ExceptionHandler(Request404Exception e) {
        log.error("404：",e.getMessage(),e);
        ModelAndView modelAndView = new ModelAndView("error/404");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        log.error("系统异常：",e.getMessage(),e);
        ModelAndView modelAndView = new ModelAndView("error/500");
        return modelAndView;
    }
}
