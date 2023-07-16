package com.hkh.ai.controller;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AuthLoginRequest;
import com.hkh.ai.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 用户认证
 */
@Controller
@AllArgsConstructor
public class AuthController {

    private final SysUserService sysUserService;

    @GetMapping("/login")
    public String login(HttpServletRequest httpServletRequest,Model model) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        if (sysUser!=null){
            return "redirect:/index";
        }
        String errorMsg = httpServletRequest.getParameter("errorMsg");
        if (StrUtil.isNotBlank(errorMsg)) {
            errorMsg = URLDecoder.decode(errorMsg, Charset.forName("utf-8"));
        }
        model.addAttribute("errorMsg", StrUtil.isNotBlank(errorMsg) ? errorMsg : "");
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(AuthLoginRequest request, HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse,Model model) throws IOException {
        Boolean pass = sysUserService.loginCheck(httpServletRequest,request.getUsername(),request.getPassword());
        if (pass){
            return "redirect:/index";
        }else {
            String errorMsg = "用户名或密码错误";
            errorMsg = URLEncodeUtil.encode(errorMsg);
            return "redirect:/login?errorMsg=" + errorMsg;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        return "redirect:/login";
    }


}
