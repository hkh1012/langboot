package com.hkh.ai.service;

import com.hkh.ai.domain.Knowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.request.KnowledgeAttachRemoveRequest;
import com.hkh.ai.request.KnowledgeRemoveRequest;
import com.hkh.ai.request.KnowledgeSaveRequest;
import com.hkh.ai.request.KnowledgeUploadRequest;
import com.hkh.ai.response.KnowledgeDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author huangkh
* @description 针对表【knowledge(知识库)】的数据库操作Service
* @createDate 2023-06-20 21:01:33
*/
public interface KnowledgeService extends IService<Knowledge> {

    void saveOne(KnowledgeSaveRequest request);

    void upload(KnowledgeUploadRequest request);

    void storeContent(MultipartFile file, String kid) throws IOException;

    KnowledgeDetailResponse detail();

    void removeAttach(KnowledgeAttachRemoveRequest request);

    void removeKnowledge(KnowledgeRemoveRequest request);
}
