package com.hkh.ai.mapper;

import com.hkh.ai.domain.Knowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【knowledge(知识库)】的数据库操作Mapper
* @createDate 2023-06-20 21:01:33
* @Entity com.hkh.openai.domain.Knowledge
*/
@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

}




