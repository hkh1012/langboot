package com.hkh.ai.config;



import com.hkh.ai.common.annotation.TokenIgnore;
import com.hkh.ai.common.exception.TokenException;
import com.hkh.ai.common.exception.TokenExpiredException;
import com.hkh.ai.component.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        if (method.isAnnotationPresent(TokenIgnore.class)) {
            return true;
        }else {
            Claims claims = tokenService.parseJWT(token);
            if (claims == null){
                throw new TokenException("非法token");
            }else {
                if (claims.getExpiration().getTime() >= new Date().getTime()){
                    httpServletRequest.setAttribute("userid",claims.get("userid"));
                    return true;
                }else {
                    throw new TokenExpiredException("token过期");
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
