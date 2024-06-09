package com.hkh.ai.controller.view;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.common.annotation.AdminRequired;
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

@Controller
@AllArgsConstructor
public class KnowledgeViewController {

    private final KnowledgeService knowledgeService;
    private final KnowledgeAttachService knowledgeAttachService;
    private final KnowledgeFragmentService knowledgeFragmentService;
    /**
     * 知识库管理页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/knowledge/index"})
    @AdminRequired
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
    @AdminRequired
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
    @AdminRequired
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
