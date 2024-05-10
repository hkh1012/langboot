package com.hkh.ai.controller;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.annotation.AdminRequired;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.*;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
import com.hkh.ai.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地知识库
 */
@RestController
@AllArgsConstructor
@RequestMapping("knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;
    private final KnowledgeShareService knowledgeShareService;
    private final KnowledgeFragmentService knowledgeFragmentService;

    private final KnowledgeAttachService knowledgeAttachService;
    private final SpecialNounService specialNounService;

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

    /**
     * 保存知识库基本信息
     * @param request
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "save")
    @AdminRequired
    public ResultData save(@RequestBody KnowledgeSaveRequest request, HttpServletRequest httpServletRequest){
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        knowledgeService.saveOne(request,sysUser);
        return ResultData.success("保存知识库成功");
    }

    /**
     * 上传知识库附件
     * @param request
     * @return
     */
    @PostMapping(value = "attach/upload")
    @AdminRequired
    public ResultData upload(KnowledgeUploadRequest request){
        knowledgeService.upload(request);
        return ResultData.success("上传知识库文件成功");
    }

    /**
     * 查询个人所有知识库
     * @param httpServletRequest
     * @return
     */
    @GetMapping("list")
    public ResultData<List<KnowledgeListResponse>> list(HttpServletRequest httpServletRequest){
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        Map<String,Object> map = new HashMap<>();
        map.put("uid",sysUser.getId());
        List<Knowledge> mineList = knowledgeService.listByMap(map);
        List<KnowledgeShare> shareList = knowledgeShareService.listByMap(map);
        List<KnowledgeListResponse> result = knowledgeService.all(mineList,shareList);
        return ResultData.success(result,"查询成功");
    }

    /**
     * 查询个人所有知识库
     * @param httpServletRequest
     * @return
     */
    @GetMapping("all")
    public ResultData<List<Knowledge>> all(HttpServletRequest httpServletRequest){
        List<Knowledge> list = knowledgeService.list();
        return ResultData.success(list,"查询成功");
    }


    @GetMapping("detail/{kid}")
    public ResultData<KnowledgeDetailResponse> detail(@PathVariable(name = "kid") String kid){
        KnowledgeDetailResponse detail = knowledgeService.detail(kid);
        return ResultData.success(detail,"查询成功");
    }

    @PostMapping("attach/remove")
    @AdminRequired
    public ResultData removeAttach(@RequestBody KnowledgeAttachRemoveRequest request){
        knowledgeService.removeAttach(request);
        return ResultData.success("删除知识库附件成功");
    }

    @PostMapping("remove")
    @AdminRequired
    public ResultData remove(@RequestBody KnowledgeRemoveRequest request){
        knowledgeService.removeKnowledge(request);
        return ResultData.success("删除知识库成功");
    }

    @PostMapping("fragment/remove")
    @AdminRequired
    public ResultData fragmentRemove(@RequestBody KnowledgeFragmentRemoveRequest request){
        knowledgeFragmentService.removeFragment(request);
        return ResultData.success("删除知识片段成功");
    }

    @PostMapping("fragment/save")
    @AdminRequired
    public ResultData fragmentSave(@RequestBody KnowledgeFragmentSaveRequest request){
        knowledgeFragmentService.saveFragment(request);
        return ResultData.success("删除知识片段成功");
    }
}
