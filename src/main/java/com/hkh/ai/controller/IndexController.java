package com.hkh.ai.controller;

import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页
 */
@Controller
@AllArgsConstructor
public class IndexController {

    @GetMapping(value = {"/"})
    public String root(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        if (sysUser != null){
            return "redirect:/index";
        }else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = {"/index"})
    public String index(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "index";
    }
}
