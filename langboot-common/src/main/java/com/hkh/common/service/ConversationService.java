package com.hkh.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.domain.Conversation;

import java.util.List;

/**
* @author huangkh
* @description 针对表【conversation(对话)】的数据库操作Service
* @createDate 2023-06-20 16:58:23
*/
public interface ConversationService extends IService<Conversation> {

    int saveConversation(Integer userId, String sessionId, String content, String qa);

    List<Conversation> listBySessionId(String sessionId);

    void removeBySid(String sid);

    List<Conversation> history(String sid);
}
