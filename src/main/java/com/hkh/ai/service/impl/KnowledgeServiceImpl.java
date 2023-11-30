package com.hkh.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.config.SysConfig;
import com.hkh.ai.domain.*;
import com.hkh.ai.chain.loader.ResourceLoader;
import com.hkh.ai.chain.loader.ResourceLoaderFactory;
import com.hkh.ai.request.*;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.response.KnowledgeListResponse;
import com.hkh.ai.service.EmbeddingService;
import com.hkh.ai.service.ExampleAttachService;
import com.hkh.ai.service.KnowledgeAttachService;
import com.hkh.ai.service.KnowledgeService;
import com.hkh.ai.mapper.KnowledgeMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private final ExampleAttachService exampleAttachService;
    private final SysConfig sysConfig;
    private final ResourceLoaderFactory resourceLoaderFactory;
    @Override
    public void saveOne(KnowledgeSaveRequest request, SysUser sysUser) {
        Knowledge knowledge = new Knowledge();
        knowledge.setKid(RandomUtil.randomString(10));
        knowledge.setUid(sysUser.getId());
        knowledge.setKname(request.getKname());
        knowledge.setCreateTime(LocalDateTime.now());
        save(knowledge);
        storeContent(request.getFile(),knowledge.getKid(),true);
    }


    @Override
    public void upload(KnowledgeUploadRequest request) {
        storeContent(request.getFile(), request.getKid(),false);
    }

    @Override
    public void storeContent(MultipartFile file, String kid,Boolean firstTime) {
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
        try {
            content = resourceLoader.getContent(file.getInputStream());
            chunkList = resourceLoader.getChunkList(content);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("store content occur exception ",e);
        }
        knowledgeAttach.setContent(content);
        knowledgeAttach.setCreateTime(LocalDateTime.now());
        knowledgeAttachService.save(knowledgeAttach);
        embeddingService.storeEmbeddings(chunkList,kid,docId,firstTime);
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
        embeddingService.removeByDocId(request.getKid(),request.getDocId());
    }

    @Override
    public void removeKnowledge(KnowledgeRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("kid",request.getKid());
        this.removeByMap(map);
        knowledgeAttachService.removeByMap(map);
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
    public void uploadExample(KnowledgeUploadExampleRequest request) {
        String fileName = request.getFile().getOriginalFilename();
        List<String> chunkList = new ArrayList<>();
        ExampleAttach exampleAttach = new ExampleAttach();
        exampleAttach.setKid(request.getKid());
        String docId = RandomUtil.randomString(10);
        exampleAttach.setDocId(docId);
        exampleAttach.setDocName(fileName);
        exampleAttach.setDocType(fileName.substring(fileName.lastIndexOf(".")+1));
        String content = "";
        ResourceLoader resourceLoader = resourceLoaderFactory.getLoaderByFileType(exampleAttach.getDocType());
        try {
            content = resourceLoader.getContent(request.getFile().getInputStream());
            chunkList = resourceLoader.getChunkList(content);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("store content occur exception ",e);
        }
        exampleAttach.setContent(content);
        exampleAttach.setCreateTime(LocalDateTime.now());
        exampleAttachService.save(exampleAttach);
        embeddingService.storeExampleEmbeddings(chunkList, request.getKid(), docId,true);
    }

    @Override
    public void removeExample(ExampleRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("kid",request.getKid());
        exampleAttachService.removeByMap(map);
        embeddingService.removeExampleByKid(request.getKid());
    }

    @Override
    public List<ExampleAttach> listExampleByMap(Map<String, Object> map) {
        return exampleAttachService.listByMap(map);
    }

}




