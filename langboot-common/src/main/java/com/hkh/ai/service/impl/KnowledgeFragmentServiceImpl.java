package com.hkh.ai.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.KnowledgeFragment;
import com.hkh.ai.request.KnowledgeFragmentPageRequest;
import com.hkh.ai.request.KnowledgeFragmentRemoveRequest;
import com.hkh.ai.request.KnowledgeFragmentSaveRequest;
import com.hkh.ai.service.KnowledgeFragmentService;
import com.hkh.ai.service.EmbeddingService;
import com.hkh.ai.mapper.KnowledgeFragmentMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
* @author huangkh
* @description 针对表【knowledge_fragment(知识片段)】的数据库操作Service实现
* @createDate 2023-12-14 16:43:31
*/
@Service
@AllArgsConstructor
public class KnowledgeFragmentServiceImpl extends ServiceImpl<KnowledgeFragmentMapper, KnowledgeFragment>
    implements KnowledgeFragmentService {

    private final EmbeddingService embeddingService;
    @Override
    public PageInfo<KnowledgeFragment> pageInfo(KnowledgeFragmentPageRequest knowledgeFragmentPageRequest) {
        QueryWrapper<KnowledgeFragment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kid",knowledgeFragmentPageRequest.getKid());
        if (StringUtils.isNotBlank(knowledgeFragmentPageRequest.getDocId())){
            queryWrapper.eq("doc_id",knowledgeFragmentPageRequest.getDocId());
        }
        if (StringUtils.isNotBlank(knowledgeFragmentPageRequest.getSearchContent())){
            queryWrapper.like("content",knowledgeFragmentPageRequest.getSearchContent());
        }
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(knowledgeFragmentPageRequest.getPageNum(), knowledgeFragmentPageRequest.getPageSize());
        PageInfo<KnowledgeFragment> pageInfo = new PageInfo<>(list(queryWrapper));
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFragment(KnowledgeFragmentRemoveRequest request) {
        QueryWrapper<KnowledgeFragment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kid",request.getKid());
        queryWrapper.eq("fid",request.getFid());
        this.remove(queryWrapper);
        embeddingService.removeByKidAndFid(request.getKid(),request.getFid());
    }

    @Override
    public void saveFragment(KnowledgeFragmentSaveRequest request) {
        String fid = RandomUtil.randomString(16);
        String docId = "";
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setKid(request.getKid());
        fragment.setFid(fid);
        fragment.setDocId(docId);
        fragment.setIdx(0);
        fragment.setContent(request.getContent());
        fragment.setCreateTime(LocalDateTime.now());
        this.save(fragment);
        embeddingService.saveFragment(request.getKid(),docId,fid,request.getContent());

    }
}




