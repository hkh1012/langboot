package com.hkh.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.domain.domain.KnowledgeShare;
import com.hkh.common.service.KnowledgeShareService;
import com.hkh.common.mapper.KnowledgeShareMapper;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【knowledge_share(知识库分享表)】的数据库操作Service实现
* @createDate 2023-08-21 11:31:38
*/
@Service
public class KnowledgeShareServiceImpl extends ServiceImpl<KnowledgeShareMapper, KnowledgeShare>
    implements KnowledgeShareService{

}




