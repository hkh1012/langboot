package com.hkh.ai.service;

import com.hkh.ai.domain.ExampleAttach;
import com.hkh.ai.domain.Knowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.domain.KnowledgeShare;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author huangkh
* @description 针对表【knowledge(知识库)】的数据库操作Service
* @createDate 2023-06-20 21:01:33
*/
public interface KnowledgeService extends IService<Knowledge> {

    void saveOne(KnowledgeSaveRequest request, SysUser sysUser);

    void upload(KnowledgeUploadRequest request);

    void storeContent(MultipartFile file, String kid,Boolean firstTime) throws IOException;

    KnowledgeDetailResponse detail(String kid);

    void removeAttach(KnowledgeAttachRemoveRequest request);

    void removeKnowledge(KnowledgeRemoveRequest request);

    List<KnowledgeListResponse> all(List<Knowledge> mineList, List<KnowledgeShare> shareList);

    void uploadExample(KnowledgeUploadExampleRequest request);

    void removeExample(ExampleRemoveRequest request);

    List<ExampleAttach> listExampleByMap(Map<String, Object> map);
}
