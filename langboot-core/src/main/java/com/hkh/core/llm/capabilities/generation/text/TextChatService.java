package com.hkh.core.llm.capabilities.generation.text;

import com.hkh.domain.domain.Conversation;
import com.hkh.domain.domain.CustomChatMessage;
import com.hkh.domain.domain.SysUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * 文本聊天服务接口
 * @author huangkh
 */
public interface TextChatService {
    /**
     * 流式聊天
     * @param request
     * @param nearestList
     * @param history
     * @param sseEmitter
     * @param sysUser
     */
    void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser) throws IOException;

    /**
     * 阻塞推理
     * @param content
     * @return
     */
    String blockCompletion(String content);
}
