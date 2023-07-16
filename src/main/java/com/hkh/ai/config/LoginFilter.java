package com.hkh.ai.config;

import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class LoginFilter implements Filter {

    @Value("${request.whiteurls}")
    private String unNeededLoginUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String path = httpServletRequest.getRequestURL().toString();
        boolean match = Arrays.stream(unNeededLoginUrls.split(",")).anyMatch(path::contains);
        if (!match){
            HttpSession session = httpServletRequest.getSession();
            SysUser sysUser = (SysUser) session.getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            if (sysUser==null){
                httpServletResponse.sendRedirect("/login");
            }else {
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
