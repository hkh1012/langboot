package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.request.KnowledgeAttachRemoveRequest;
import com.hkh.ai.request.KnowledgeRemoveRequest;
import com.hkh.ai.request.KnowledgeSaveRequest;
import com.hkh.ai.request.KnowledgeUploadRequest;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.service.KnowledgeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 本地知识库
 */
@RestController
@AllArgsConstructor
@RequestMapping("knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @PostMapping(value = "save")
    public ResultData save(KnowledgeSaveRequest request){
        knowledgeService.saveOne(request);
        return ResultData.success("保存知识库成功");
    }

    @PostMapping(value = "upload")
    public ResultData upload(KnowledgeUploadRequest request){
        knowledgeService.upload(request);
        return ResultData.success("上传知识库文件成功");
    }

    @GetMapping("detail")
    public ResultData<KnowledgeDetailResponse> detail(){
        KnowledgeDetailResponse detail = knowledgeService.detail();
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
