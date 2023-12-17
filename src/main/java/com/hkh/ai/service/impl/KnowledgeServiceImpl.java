package com.hkh.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkh.ai.config.SysConfig;
import com.hkh.ai.domain.*;
import com.hkh.ai.chain.loader.ResourceLoader;
import com.hkh.ai.chain.loader.ResourceLoaderFactory;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
import com.hkh.ai.service.*;
import com.hkh.ai.mapper.KnowledgeMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
* @author huangkh
* @description 针对表【knowledge(知识库)】的数据库操作Service实现
* @createDate 2023-06-20 21:01:33
*/
@Service
@AllArgsConstructor
@Slf4j
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService{

    private final EmbeddingService embeddingService;
    private final KnowledgeAttachService knowledgeAttachService;
    private final KnowledgeFragmentService knowledgeFragmentService;
    private final ResourceLoaderFactory resourceLoaderFactory;
    @Override
    public void saveOne(KnowledgeSaveRequest request, SysUser sysUser) {
        if (StringUtils.isBlank(request.getKid())){
            String kid = RandomUtil.randomString(10);
            Knowledge knowledge = new Knowledge();
            knowledge.setKid(kid);
            knowledge.setUid(sysUser.getId());
            knowledge.setKname(request.getKname());
            knowledge.setDescription(request.getDescription());
            knowledge.setCreateTime(LocalDateTime.now());
            save(knowledge);
            embeddingService.createSchema(kid);
        }else {
            Knowledge knowledge = this.getById(request.getId());
            knowledge.setKid(request.getKid());
            knowledge.setUid(request.getUid());
            knowledge.setKname(request.getKname());
            knowledge.setDescription(request.getDescription());
            saveOrUpdate(knowledge);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(KnowledgeUploadRequest request) {
        storeContent(request.getFile(), request.getKid());
    }

    @Override
    public void storeContent(MultipartFile file, String kid) {
        String fileName = file.getOriginalFilename();
        List<String> chunkList = new ArrayList<>();
        KnowledgeAttach knowledgeAttach = new KnowledgeAttach();
        knowledgeAttach.setKid(kid);
        String docId = RandomUtil.randomString(10);
        knowledgeAttach.setDocId(docId);
        knowledgeAttach.setDocName(fileName);
        knowledgeAttach.setDocType(fileName.substring(fileName.lastIndexOf(".")+1));
        String content = "";
        ResourceLoader resourceLoader = resourceLoaderFactory.getLoaderByFileType(knowledgeAttach.getDocType());
        List<String> fids = new ArrayList<>();
        try {
            content = resourceLoader.getContent(file.getInputStream());
            chunkList = resourceLoader.getChunkList(content);
            List<KnowledgeFragment> fragments = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (int i = 0; i < chunkList.size(); i++) {
                String fid = RandomUtil.randomString(16);
                fids.add(fid);
                KnowledgeFragment knowledgeFragment = new KnowledgeFragment();
                knowledgeFragment.setKid(kid);
                knowledgeFragment.setDocId(docId);
                knowledgeFragment.setFid(fid);
                knowledgeFragment.setIdx(i);
                knowledgeFragment.setContent(chunkList.get(i));
                knowledgeFragment.setCreateTime(now);
                fragments.add(knowledgeFragment);
            }
            knowledgeFragmentService.saveBatch(fragments);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("store content occur exception ",e);
        }
        knowledgeAttach.setContent(content);
        knowledgeAttach.setCreateTime(LocalDateTime.now());
        knowledgeAttachService.save(knowledgeAttach);
        embeddingService.storeEmbeddings(chunkList,kid,docId,fids);
    }

    @Override
    public KnowledgeDetailResponse detail(String kid) {
        KnowledgeDetailResponse detail  = new KnowledgeDetailResponse();
        Map<String,Object> map = new HashMap<>();
        map.put("kid",kid);
        List<Knowledge> knowledgeList = this.listByMap(map);
        if (knowledgeList != null && knowledgeList.size() >0){
            Knowledge knowledge = knowledgeList.get(0);
            BeanUtil.copyProperties(knowledge,detail);

            List<KnowledgeAttach> attachList = knowledgeAttachService.listByMap(map);
            detail.setAttachList(attachList);
            return detail;
        }
       return null;
    }

    @Override
    public void removeAttach(KnowledgeAttachRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("kid",request.getKid());
        map.put("doc_id",request.getDocId());
        knowledgeAttachService.removeByMap(map);
        knowledgeFragmentService.removeByMap(map);
        embeddingService.removeByDocId(request.getKid(),request.getDocId());
    }

    @Override
    public void removeKnowledge(KnowledgeRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("kid",request.getKid());
        this.removeByMap(map);
        knowledgeAttachService.removeByMap(map);
        knowledgeFragmentService.removeByMap(map);
        embeddingService.removeByKid(request.getKid());
    }

    @Override
    public List<KnowledgeListResponse> all(List<Knowledge> mineList, List<KnowledgeShare> shareList) {
        List<KnowledgeListResponse> list = new ArrayList<>();
        for (Knowledge knowledge : mineList) {
            KnowledgeListResponse item = new KnowledgeListResponse();
            BeanUtil.copyProperties(knowledge,item);
            item.setRole("owner");
            Map<String,Object> map = new HashMap<>();
            map.put("kid",knowledge.getKid());
            List<KnowledgeAttach> attachList = knowledgeAttachService.listByMap(map);
            item.setAttachList(attachList);
            list.add(item);
        }
        for (KnowledgeShare knowledgeShare : shareList){
            QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("kid",knowledgeShare.getKid());
            Knowledge knowledge = getOne(queryWrapper,false);
            if (knowledge != null){
                KnowledgeListResponse item = new KnowledgeListResponse();
                BeanUtil.copyProperties(knowledge,item);
                item.setRole("sharer");
                Map<String,Object> map = new HashMap<>();
                map.put("kid",knowledge.getKid());
                List<KnowledgeAttach> attachList = knowledgeAttachService.listByMap(map);
                item.setAttachList(attachList);
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public PageInfo<Knowledge> pageInfo(KnowledgePageRequest knowledgePageRequest) {
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("kname",knowledgePageRequest.getSearchContent())
                .or().like("description",knowledgePageRequest.getSearchContent())
        ;
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(knowledgePageRequest.getPageNum(), knowledgePageRequest.getPageSize());
        PageInfo<Knowledge> pageInfo = new PageInfo<>(list(queryWrapper));
        pageInfo.getList().stream().forEach(item -> {
            if (StringUtils.isBlank(item.getDescription())) {
                item.setDescription("");
            }
        });
        return pageInfo;
    }

    @Override
    public Knowledge getOneByKid(String kid) {
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kid",kid);
        Knowledge knowledge = this.getOne(queryWrapper, false);
        return knowledge;
    }

}




