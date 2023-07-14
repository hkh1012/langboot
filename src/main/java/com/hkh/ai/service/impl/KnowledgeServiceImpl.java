package com.hkh.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.config.SysConfig;
import com.hkh.ai.domain.Knowledge;
import com.hkh.ai.domain.KnowledgeAttach;
import com.hkh.ai.chain.loader.ResourceLoader;
import com.hkh.ai.chain.loader.ResourceLoaderFactory;
import com.hkh.ai.request.KnowledgeAttachRemoveRequest;
import com.hkh.ai.request.KnowledgeRemoveRequest;
import com.hkh.ai.request.KnowledgeSaveRequest;
import com.hkh.ai.request.KnowledgeUploadRequest;
import com.hkh.ai.response.KnowledgeDetailResponse;
import com.hkh.ai.service.EmbeddingService;
import com.hkh.ai.service.KnowledgeAttachService;
import com.hkh.ai.service.KnowledgeService;
import com.hkh.ai.mapper.KnowledgeMapper;
import lombok.AllArgsConstructor;
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
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService{

    private final EmbeddingService embeddingService;
    private final KnowledgeAttachService knowledgeAttachService;
    private final SysConfig sysConfig;
    private final ResourceLoaderFactory resourceLoaderFactory;
    @Override
    public void saveOne(KnowledgeSaveRequest request) {
        Knowledge knowledge = new Knowledge();
        knowledge.setKid(RandomUtil.randomString(10));
        knowledge.setKname(request.getKname());
        knowledge.setCreateTime(LocalDateTime.now());
        save(knowledge);
        storeContent(request.getFile(),knowledge.getKid());
    }

    @Override
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
        try {
            content = resourceLoader.getContent(file.getInputStream());
            chunkList = resourceLoader.getChunkList(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        knowledgeAttach.setContent(content);
        knowledgeAttach.setCreateTime(LocalDateTime.now());
        knowledgeAttachService.save(knowledgeAttach);
        embeddingService.storeEmbeddings(chunkList,kid,docId);
    }

    @Override
    public KnowledgeDetailResponse detail() {
        List<Knowledge> list = this.list();
        if (list!=null && list.size()>0){
            KnowledgeDetailResponse detail  = new KnowledgeDetailResponse();
            Knowledge knowledge = list.get(0);
            BeanUtil.copyProperties(knowledge,detail);
            Map<String,Object> map = new HashMap<>();
            map.put("kid",knowledge.getKid());
            List<KnowledgeAttach> attachList = knowledgeAttachService.listByMap(map);
            detail.setAttachList(attachList);
            return detail;
        }
        return null;
    }

    @Override
    public void removeAttach(KnowledgeAttachRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("doc_id",request.getDocId());
        knowledgeAttachService.removeByMap(map);
        embeddingService.removeByDocId(request.getDocId());
    }

    @Override
    public void removeKnowledge(KnowledgeRemoveRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("kid",request.getKid());
        this.removeByMap(map);
        knowledgeAttachService.removeByMap(map);
        embeddingService.removeByKid(request.getKid());
    }

}




