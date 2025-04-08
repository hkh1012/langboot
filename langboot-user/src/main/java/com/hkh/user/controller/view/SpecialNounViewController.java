package com.hkh.user.controller.view;

import com.github.pagehelper.PageInfo;
import com.hkh.common.common.annotation.AdminRequired;
import com.hkh.common.service.SpecialNounService;
import com.hkh.domain.constant.SysConstants;
import com.hkh.domain.domain.SpecialNoun;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.SpecialNounPageRequest;
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
