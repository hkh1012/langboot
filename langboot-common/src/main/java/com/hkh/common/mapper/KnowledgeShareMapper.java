package com.hkh.common.mapper;

import com.hkh.domain.domain.KnowledgeShare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【knowledge_share(知识库分享表)】的数据库操作Mapper
* @createDate 2023-08-21 11:31:38
* @Entity com.hkh.domain.domain.KnowledgeShare
*/
@Mapper
public interface KnowledgeShareMapper extends BaseMapper<KnowledgeShare> {

}




