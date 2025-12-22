package com.hkh.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.domain.KnowledgeAttach;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【knowledge_attach(知识库附件)】的数据库操作Mapper
* @createDate 2023-06-20 21:01:41
* @Entity com.hkh.openai.domain.KnowledgeAttach
*/
@Mapper
public interface KnowledgeAttachMapper extends BaseMapper<KnowledgeAttach> {

}




