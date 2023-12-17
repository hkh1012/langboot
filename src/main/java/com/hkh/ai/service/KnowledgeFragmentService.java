package com.hkh.ai.service;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.KnowledgeFragment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.request.KnowledgeFragmentPageRequest;
import com.hkh.ai.request.KnowledgeFragmentRemoveRequest;
import com.hkh.ai.request.KnowledgeFragmentSaveRequest;

/**
* @author huangkh
* @description 针对表【knowledge_fragment(知识片段)】的数据库操作Service
* @createDate 2023-12-14 16:43:31
*/
public interface KnowledgeFragmentService extends IService<KnowledgeFragment> {

    PageInfo<KnowledgeFragment> pageInfo(KnowledgeFragmentPageRequest knowledgeFragmentPageRequest);

    void removeFragment(KnowledgeFragmentRemoveRequest request);

    void saveFragment(KnowledgeFragmentSaveRequest request);
}
