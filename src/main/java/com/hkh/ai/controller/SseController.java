package com.hkh.ai.controller;

import com.hkh.ai.chain.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.vision.VisionChatService;
import com.hkh.ai.chain.vectorizer.local.LocalAiVectorization;
import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.*;
import com.hkh.ai.request.AudioChatRequest;
import com.hkh.ai.request.VisionChatRequest;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * sse 聊天
 */
@Controller
@RequestMapping(path = "sse")
@AllArgsConstructor
@Slf4j
public class SseController {
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    private final TextChatService textChatService;
    private final AudioChatService audioChatService;
    private final VisionChatService visionChatService;
    private final VectorStore vectorStore;
    private final Vectorization vectorization;

    private final EmbeddingService embeddingService;
    private final ConversationService conversationService;
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
            if (useLk){
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
            textChatService.streamChat(customChatMessage,nearestList,history,sseEmitter,sysUser);
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
        if (request.getUseLk()){
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
        audioChatService.audioChat(customChatMessage,nearestList,history,sseEmitter,sysUser, request.getMediaId());
        return ResultData.success(audioChatResponse,"发送成功");
    }

    @PostMapping(path = "visionChat")
    @ResponseBody
    public ResultData<String> visionChat(HttpServletRequest httpServletRequest, @RequestBody VisionChatRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        List<MediaFile> mediaFileList = mediaFileService.listByMfids(request.getMediaIds());
        int questionCid = conversationService.saveConversation(sysUser.getId(),request.getSid(), request.getContent(), "Q");
        mediaFileService.updateWithCid(request.getMediaIds(),questionCid);
        List<String> httpUrls = mediaFileList.stream().map((item)->item.getHttpUrl()).collect(Collectors.toList());
        String result = visionChatService.visionCompletion(request.getContent(), httpUrls);
        int answerCid = conversationService.saveConversation(sysUser.getId(),request.getSid(), result, "A");
        return ResultData.success(result,"发送成功");

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
