package com.hkh.ai.controller.view;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.common.annotation.AdminRequired;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SpecialNoun;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.SpecialNounPageRequest;
import com.hkh.ai.service.SpecialNounService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SpecialNounViewController {

    private final SpecialNounService specialNounService;

    /**
     * 专有名词管理页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/special/index"})
    @AdminRequired
    public String specialIndex(HttpServletRequest request, Model model, SpecialNounPageRequest specialNounPageRequest) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        model.addAttribute("sysUser",sysUser);
        PageInfo<SpecialNoun> pageInfo = specialNounService.pageInfo(specialNounPageRequest);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("formData",specialNounPageRequest);
        return "special/index";
    }
}
