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

    @GetMapping(value = {"/chat"})
    public String chat(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "chat";
    }

    @GetMapping(value = {"/summary"})
    public String summary(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "summary";
    }

    @GetMapping(value = {"/classic"})
    public String category(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "classic";
    }

    @GetMapping(value = {"/duplication"})
    public String duplication(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "duplication";
    }

    @GetMapping(value = {"/sentiment"})
    public String sentiment(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "sentiment";
    }

    @GetMapping(value = {"/keyword"})
    public String keyword(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "keyword";
    }

    @GetMapping(value = {"/translate"})
    public String translate(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "translate";
    }

    @GetMapping(value = {"/textToImage"})
    public String textToImage(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "textToImage";
    }
}
