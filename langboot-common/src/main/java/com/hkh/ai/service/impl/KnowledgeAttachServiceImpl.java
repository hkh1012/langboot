package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.KnowledgeAttach;
import com.hkh.ai.request.KnowledgeAttachPageRequest;
import com.hkh.ai.service.KnowledgeAttachService;
import com.hkh.ai.mapper.KnowledgeAttachMapper;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【knowledge_attach(知识库附件)】的数据库操作Service实现
* @createDate 2023-06-20 21:01:41
*/
@Service
public class KnowledgeAttachServiceImpl extends ServiceImpl<KnowledgeAttachMapper, KnowledgeAttach>
    implements KnowledgeAttachService{

    @Override
    public PageInfo<KnowledgeAttach> pageInfo(KnowledgeAttachPageRequest knowledgeAttachPageRequest) {
        QueryWrapper<KnowledgeAttach> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kid",knowledgeAttachPageRequest.getKid());
        queryWrapper.and(
            (wrapper) -> {
                wrapper.like("doc_name",knowledgeAttachPageRequest.getSearchContent()).or()
                    .like("content",knowledgeAttachPageRequest.getSearchContent());
        });
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(knowledgeAttachPageRequest.getPageNum(), knowledgeAttachPageRequest.getPageSize());
        PageInfo<KnowledgeAttach> pageInfo = new PageInfo<>(list(queryWrapper));
        return pageInfo;
    }
}




