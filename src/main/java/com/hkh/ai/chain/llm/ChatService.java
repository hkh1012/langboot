package com.hkh.ai.chain.llm;

import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * LLM聊天大模型
 */
public interface ChatService {

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

    /**
     * 函数调用：输出结构化数据
     * @param content
     * @return
     */
    String functionCompletion(String content, String functionName,String description ,Class clazz);

    /**
     *
     * @return
     */
    String audioToText(File audio,String prompt);

    void audioChat(CustomChatMessage customChatMessage, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser,String mediaId);

    InputStream createSpeech(String content);
}
