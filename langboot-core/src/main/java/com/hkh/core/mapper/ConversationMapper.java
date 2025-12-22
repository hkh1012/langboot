package com.hkh.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.domain.Conversation;
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




