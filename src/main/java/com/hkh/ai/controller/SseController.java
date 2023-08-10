package com.hkh.ai.controller;

import com.hkh.ai.chain.llm.ChatService;
import com.hkh.ai.chain.llm.ChatServiceFactory;
import com.hkh.ai.chain.vectorizer.LocalAiVectorization;
import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorizer.VectorizationFactory;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.chain.vectorstore.VectorStoreFactory;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.hkh.ai.service.EmbeddingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sse 聊天
 */
@Controller
@RequestMapping(path = "sse")
@AllArgsConstructor
public class SseController {
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    private final ChatServiceFactory chatServiceFactory;
    private final VectorStoreFactory vectorStoreFactory;
    private final VectorizationFactory vectorizationFactory;

    private final EmbeddingService embeddingService;

    private final ConversationService conversationService;

    @PostMapping(path = "send")
    @ResponseBody
    public ResultData send(HttpServletRequest httpServletRequest, String sessionId, String content,String sid,Boolean useLk,Boolean useHistory) {
        SseEmitter sseEmitter = sseCache.get(sessionId);
        if (sseEmitter != null) {
            SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
            List<Conversation> history = new ArrayList<>();
            if (useHistory){
                history = conversationService.history(sid);
            }
            CustomChatMessage customChatMessage = new CustomChatMessage(content,sid);
            ChatService chatService = chatServiceFactory.getChatService();
            List<String> nearestList = new ArrayList<>();
            if (useLk){
                VectorStore vectorStore = vectorStoreFactory.getVectorStore();
                Vectorization vectorization = vectorizationFactory.getEmbedding();
                if (vectorization instanceof LocalAiVectorization){
                    // 使用向量数据库内置的嵌入向量模型
                    nearestList = vectorStore.nearest(content);
                }else {
                    // 使用外部的嵌入向量模型
                    List<Double> queryVector = embeddingService.getQueryVector(content);
                    nearestList = vectorStore.nearest(queryVector);
                }
            }
            chatService.streamChat(customChatMessage,nearestList,history,sseEmitter,sysUser);
        }
        return ResultData.success("发送成功");
    }

    @GetMapping(path = "over")
    public String over(String sessionId) {
        SseEmitter sseEmitter = sseCache.get(sessionId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            sseCache.remove(sessionId);
        }
        return "over";
    }

    @ResponseBody
    @GetMapping(path = "subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String sessionId) {
        SseEmitter sseEmitter = new SseEmitter(3600 * 1000L);
        sseCache.put(sessionId, sseEmitter);
        System.out.println("add " + sessionId);
        sseEmitter.onTimeout(() -> {
            System.out.println(sessionId + "超时");
            sseCache.remove(sessionId);
        });
        sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
        return sseEmitter;
    }

}
