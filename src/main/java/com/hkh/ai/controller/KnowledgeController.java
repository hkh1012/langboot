package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.*;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
import com.hkh.ai.service.KnowledgeFragmentService;
import com.hkh.ai.service.KnowledgeService;
import com.hkh.ai.service.KnowledgeShareService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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

    /**
     * 保存知识库基本信息
     * @param request
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "save")
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


    @GetMapping("detail/{kid}")
    public ResultData<KnowledgeDetailResponse> detail(@PathVariable(name = "kid") String kid){
        KnowledgeDetailResponse detail = knowledgeService.detail(kid);
        return ResultData.success(detail,"查询成功");
    }

    @PostMapping("attach/remove")
    public ResultData removeAttach(@RequestBody KnowledgeAttachRemoveRequest request){
        knowledgeService.removeAttach(request);
        return ResultData.success("删除知识库附件成功");
    }

    @PostMapping("remove")
    public ResultData remove(@RequestBody KnowledgeRemoveRequest request){
        knowledgeService.removeKnowledge(request);
        return ResultData.success("删除知识库成功");
    }

    @PostMapping("fragment/remove")
    public ResultData fragmentRemove(@RequestBody KnowledgeFragmentRemoveRequest request){
        knowledgeFragmentService.removeFragment(request);
        return ResultData.success("删除知识片段成功");
    }

    @PostMapping("fragment/save")
    public ResultData fragmentSave(@RequestBody KnowledgeFragmentSaveRequest request){
        knowledgeFragmentService.saveFragment(request);
        return ResultData.success("删除知识片段成功");
    }
}
