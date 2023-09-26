package com.hkh.ai.controller;

import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    /**
     * 首页
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/index"})
    public String index(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "index";
    }

    /**
     * 对话页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/chat"})
    public String chat(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "chat";
    }

    /**
     * 摘要页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/summary"})
    public String summary(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "summary";
    }


    /**
     * 分类页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/classic"})
    public String category(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "classic";
    }

    /**
     * 查重页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/duplication"})
    public String duplication(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "duplication";
    }

    /**
     * 情感分析页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/sentiment"})
    public String sentiment(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "sentiment";
    }

    /**
     * 关坚持提取页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/keyword"})
    public String keyword(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "keyword";
    }

    /**
     * 翻译页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/translate"})
    public String translate(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "translate";
    }

    /**
     * 内容安全页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/security"})
    public String security(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "security";
    }

    /**
     * 文生图页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/textToImage"})
    public String textToImage(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "textToImage";
    }

    /**
     * 函数调用
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/function"})
    public String function(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "function";
    }

    /**
     * 代理agent
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/agent"})
    public String agent(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "agent";
    }
}
