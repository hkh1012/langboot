package com.hkh.ai.service;

import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.KnowledgeAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.request.KnowledgeAttachPageRequest;

/**
* @author huangkh
* @description 针对表【knowledge_attach(知识库附件)】的数据库操作Service
* @createDate 2023-06-20 21:01:41
*/
public interface KnowledgeAttachService extends IService<KnowledgeAttach> {

    PageInfo<KnowledgeAttach> pageInfo(KnowledgeAttachPageRequest knowledgeAttachPageRequest);
}
