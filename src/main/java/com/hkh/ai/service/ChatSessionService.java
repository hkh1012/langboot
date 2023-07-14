package com.hkh.ai.service;

import com.hkh.ai.domain.ChatSession;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.ChatSessionSaveRequest;

import java.util.List;

/**
* @author huangkh
* @description 针对表【chat_session(用户会话)】的数据库操作Service
* @createDate 2023-06-20 16:47:01
*/
public interface ChatSessionService extends IService<ChatSession> {

    void saveChatSession(ChatSessionSaveRequest request, SysUser sysUser);

    List<ChatSession> listByUserId(Integer id);

    void removeBySid(String sid, SysUser sysUser);
}
