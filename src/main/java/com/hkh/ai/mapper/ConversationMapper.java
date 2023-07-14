package com.hkh.ai.mapper;

import com.hkh.ai.domain.Conversation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【conversation(对话)】的数据库操作Mapper
* @createDate 2023-06-20 16:58:23
* @Entity com.hkh.openai.domain.Conversation
*/
@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {

}




