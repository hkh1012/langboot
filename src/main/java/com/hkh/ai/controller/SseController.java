package com.hkh.ai.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.hkh.ai.chain.llm.ChatService;
import com.hkh.ai.chain.llm.ChatServiceFactory;
import com.hkh.ai.chain.retrieve.PromptRetrieverProperties;
import com.hkh.ai.chain.vectorizer.LocalAiVectorization;
import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorizer.VectorizationFactory;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.chain.vectorstore.VectorStoreFactory;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.ChatRequestLog;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AudioChatRequest;
import com.hkh.ai.response.AudioChatResponse;
import com.hkh.ai.service.ChatRequestLogService;
import com.hkh.ai.service.ConversationService;
import com.hkh.ai.service.EmbeddingService;
import com.hkh.ai.service.MediaFileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sse 聊天
 */
@Controller
@RequestMapping(path = "sse")
@AllArgsConstructor
@Slf4j
public class SseController {
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    private final ChatServiceFactory chatServiceFactory;
    private final VectorStoreFactory vectorStoreFactory;
    private final VectorizationFactory vectorizationFactory;

    private final EmbeddingService embeddingService;
    private final ConversationService conversationService;
    private final PromptRetrieverProperties promptRetrieverProperties;
    private final ChatRequestLogService chatRequestLogService;

    private final MediaFileService mediaFileService;
    @SneakyThrows
    @PostMapping(path = "send")
    @ResponseBody
    public ResultData<List<String>> send(HttpServletRequest httpServletRequest, String sessionId, String content,String kid,String sid,Boolean useLk,Boolean useHistory) {
        SseEmitter sseEmitter = sseCache.get(sessionId);
        List<String> nearestList = new ArrayList<>();
        if (sseEmitter != null) {
            SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
            ChatRequestLog chatRequestLog = new ChatRequestLog();
            chatRequestLog.setUserId(sysUser.getId());
            chatRequestLog.setKid(kid);
            chatRequestLog.setRequestTime(LocalDateTime.now());
            chatRequestLog.setContent(content);
            chatRequestLog.setCreateTime(LocalDateTime.now());
            chatRequestLogService.save(chatRequestLog);
            List<Conversation> history = new ArrayList<>();
            if (useHistory){
                history = conversationService.history(sid);
            }
            CustomChatMessage customChatMessage = new CustomChatMessage(content,sid);
            ChatService chatService = chatServiceFactory.getChatService();
            if (useLk){
                VectorStore vectorStore = vectorStoreFactory.getVectorStore();
                Vectorization vectorization = vectorizationFactory.getEmbedding();
                if (vectorization instanceof LocalAiVectorization){
                    // 使用 weaviate向量数据库内置的嵌入向量模型
                    nearestList = vectorStore.nearest(content,kid);
                }else {
                    // 使用外部的嵌入向量模型
                    List<Double> queryVector = embeddingService.getQueryVector(content);
                    nearestList = vectorStore.nearest(queryVector,kid);
                }
                log.info("知识库向量检索结果为{}",nearestList);
            }
            if (useLk && promptRetrieverProperties.isStrict() && nearestList.size() == 0){
                try {
                    String goalKeeperWords = "Sorry，本地知识库未找到相关内容，请您尝试其他方式。";
                    conversationService.saveConversation(sysUser.getId(),customChatMessage.getSessionId(), customChatMessage.getContent(), "Q");
                    conversationService.saveConversation(sysUser.getId(),customChatMessage.getSessionId(), goalKeeperWords, "A");
                    sseEmitter.send(goalKeeperWords);
                    sseEmitter.send("[END]");
                } catch (IOException e) {
                    log.error("sseEmitter send occur error",e);
                    throw new RuntimeException(e);
                }
            }else {
                chatService.streamChat(customChatMessage,nearestList,history,sseEmitter,sysUser);
            }
        }
        return ResultData.success(nearestList,"发送成功");
    }

    @PostMapping(path = "audioChat")
    @ResponseBody
    public ResultData<AudioChatResponse> audioChat(HttpServletRequest httpServletRequest, @RequestBody AudioChatRequest request) {
        SseEmitter sseEmitter = sseCache.get(request.getSessionId());
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        List<String> nearestList = new ArrayList<>();
        List<Conversation> history = new ArrayList<>();
        if (request.getUseHistory()){
            history = conversationService.history(request.getSid());
        }
        CustomChatMessage customChatMessage = new CustomChatMessage(request.getContent(), request.getSid());
        ChatService chatService = chatServiceFactory.getChatService();
        if (request.getUseLk()){
            VectorStore vectorStore = vectorStoreFactory.getVectorStore();
            Vectorization vectorization = vectorizationFactory.getEmbedding();
            if (vectorization instanceof LocalAiVectorization){
                // 使用 weaviate向量数据库内置的嵌入向量模型
                nearestList = vectorStore.nearest(request.getContent(),request.getKid());
            }else {
                // 使用外部的嵌入向量模型
                List<Double> queryVector = embeddingService.getQueryVector(request.getContent());
                nearestList = vectorStore.nearest(queryVector,request.getKid());
            }
            log.info("知识库向量检索结果为{}",nearestList);
        }
        AudioChatResponse audioChatResponse = new AudioChatResponse();
        audioChatResponse.setNearestList(nearestList);
        if (request.getUseLk() && promptRetrieverProperties.isStrict() && nearestList.size() == 0){
            String goalKeeperWords = "对不起，本地知识库未找到相关内容，请您尝试其他提问方式。";
            conversationService.saveConversation(sysUser.getId(),customChatMessage.getSessionId(), customChatMessage.getContent(), "Q");
            conversationService.saveConversation(sysUser.getId(),customChatMessage.getSessionId(), goalKeeperWords, "A");
            audioChatResponse.setContent(goalKeeperWords);
            return ResultData.success(audioChatResponse,"发送成功");
        }else {
            chatService.audioChat(customChatMessage,nearestList,history,sseEmitter,sysUser, request.getMediaId());
            return ResultData.success(audioChatResponse,"发送成功");
        }
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
