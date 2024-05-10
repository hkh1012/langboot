package com.hkh.ai.mapper;

import com.hkh.ai.domain.ChatSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【chat_session(用户会话)】的数据库操作Mapper
* @createDate 2023-06-20 16:47:01
* @Entity com.hkh.openai.domain.ChatSession
*/
@Mapper
public interface ChatSessionMapper extends BaseMapper<ChatSession> {

}




