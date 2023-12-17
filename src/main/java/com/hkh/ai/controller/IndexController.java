package com.hkh.ai.controller;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.Knowledge;
import com.hkh.ai.domain.KnowledgeAttach;
import com.hkh.ai.domain.KnowledgeFragment;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.KnowledgeAttachPageRequest;
import com.hkh.ai.request.KnowledgeFragmentPageRequest;
import com.hkh.ai.request.KnowledgePageRequest;
import com.hkh.ai.service.KnowledgeAttachService;
import com.hkh.ai.service.KnowledgeFragmentService;
import com.hkh.ai.service.KnowledgeService;
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

    private final KnowledgeService knowledgeService;
    private final KnowledgeAttachService knowledgeAttachService;
    private final KnowledgeFragmentService knowledgeFragmentService;

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

    /**
     * 代理agent(客服)
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/agent/cs"})
    public String agentCs(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "agent_cs";
    }

    /**
     * 代理agent(业绩)
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/agent/sales"})
    public String agentSales(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "agent_sales";
    }

    /**
     * 代理agent(复杂任务)
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/agent/complex"})
    public String agentComplex(HttpServletRequest request, Model model) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        return "agent_complex";
    }

    /**
     * 知识库管理页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/knowledge/index"})
    public String knowledgeIndex(HttpServletRequest request, Model model, KnowledgePageRequest knowledgePageRequest) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        PageInfo<Knowledge> pageInfo = knowledgeService.pageInfo(knowledgePageRequest);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("formData",knowledgePageRequest);
        return "knowledge/index";
    }

    /**
     * 知识库附件管理页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/knowledge/attach"})
    public String knowledgeAttach(HttpServletRequest request, Model model, KnowledgeAttachPageRequest knowledgeAttachPageRequest) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        PageInfo<KnowledgeAttach> pageInfo = knowledgeAttachService.pageInfo(knowledgeAttachPageRequest);
        Knowledge knowledge = knowledgeService.getOneByKid(knowledgeAttachPageRequest.getKid());
        model.addAttribute("knowledge",knowledge);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("formData",knowledgeAttachPageRequest);
        return "knowledge/attach";
    }

    /**
     * 知识库附件管理页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/knowledge/fragment"})
    public String knowledgeFragment(HttpServletRequest request, Model model, KnowledgeFragmentPageRequest knowledgeFragmentPageRequest) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        PageInfo<KnowledgeFragment> pageInfo = knowledgeFragmentService.pageInfo(knowledgeFragmentPageRequest);
        Knowledge knowledge = knowledgeService.getOneByKid(knowledgeFragmentPageRequest.getKid());
        model.addAttribute("knowledge",knowledge);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("formData",knowledgeFragmentPageRequest);
        return "knowledge/fragment";
    }
}
