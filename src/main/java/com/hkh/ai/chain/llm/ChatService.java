package com.hkh.ai.chain.llm;

import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * LLM聊天大模型
 */
public interface ChatService {

    void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser);
}
