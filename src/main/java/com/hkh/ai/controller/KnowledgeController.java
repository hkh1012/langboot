package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.ExampleAttach;
import com.hkh.ai.domain.Knowledge;
import com.hkh.ai.domain.KnowledgeShare;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
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

    /**
     * 新建知识库
     * @param request
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "save")
    public ResultData save(KnowledgeSaveRequest request, HttpServletRequest httpServletRequest){
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        knowledgeService.saveOne(request,sysUser);
        return ResultData.success("保存知识库成功");
    }

    /**
     * 上传知识库附件
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    public ResultData upload(KnowledgeUploadRequest request){
        knowledgeService.upload(request);
        return ResultData.success("上传知识库文件成功");
    }

    /**
     * 上传示例库
     * @param request
     * @return
     */
    @PostMapping(value = "uploadExample")
    public ResultData uploadExample(KnowledgeUploadExampleRequest request){
        knowledgeService.uploadExample(request);
        return ResultData.success("上传示例库文件成功");
    }

    /**
     * 删除示例库
     * @param request
     * @return
     */
    @PostMapping("removeExample")
    public ResultData removeExample(@RequestBody ExampleRemoveRequest request){
        knowledgeService.removeExample(request);
        return ResultData.success("删除示例库成功");
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
     * 查询示例库
     * @param httpServletRequest
     * @return
     */
    @GetMapping("example/list/{kid}")
    public ResultData<List<ExampleAttach>> exampleList(HttpServletRequest httpServletRequest,@PathVariable(value = "kid") String kid){
        Map<String,Object> map = new HashMap<>();
        map.put("kid",kid);
        List<ExampleAttach> exampleAttachList = knowledgeService.listExampleByMap(map);
        return ResultData.success(exampleAttachList,"查询成功");
    }


    @GetMapping("detail/{kid}")
    public ResultData<KnowledgeDetailResponse> detail(@PathVariable(name = "kid") String kid){
        KnowledgeDetailResponse detail = knowledgeService.detail(kid);
        return ResultData.success(detail,"查询成功");
    }

    @PostMapping("removeAttach")
    public ResultData removeAttach(@RequestBody KnowledgeAttachRemoveRequest request){
        knowledgeService.removeAttach(request);
        return ResultData.success("删除知识库附件成功");
    }

    @PostMapping("remove")
    public ResultData remove(@RequestBody KnowledgeRemoveRequest request){
        knowledgeService.removeKnowledge(request);
        return ResultData.success("删除知识库成功");
    }
}
