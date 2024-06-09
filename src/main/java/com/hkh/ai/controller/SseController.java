package com.hkh.ai.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.hkh.ai.chain.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.ai.chain.llm.capabilities.generation.image.ImageChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.chain.llm.capabilities.generation.vision.VisionChatService;
import com.hkh.ai.chain.loader.ResourceLoader;
import com.hkh.ai.chain.loader.ResourceLoaderFactory;
import com.hkh.ai.chain.plugin.search.engine.WebSearchEngine;
import com.hkh.ai.chain.vectorizer.VectorizationFactory;
import com.hkh.ai.chain.vectorizer.local.LocalAiVectorization;
import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.*;
import com.hkh.ai.request.AudioChatRequest;
import com.hkh.ai.request.ImageChatRequest;
import com.hkh.ai.request.MediaFileBase64UploadRequest;
import com.hkh.ai.request.VisionChatRequest;
import com.hkh.ai.response.AudioChatResponse;
import com.hkh.ai.response.CreateImageChatResponse;
import com.hkh.ai.service.ChatRequestLogService;
import com.hkh.ai.service.ConversationService;
import com.hkh.ai.service.EmbeddingService;
import com.hkh.ai.service.MediaFileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    private final ImageChatService imageChatService;

    private final VectorStore vectorStore;
    private final Vectorization vectorization;

    private final EmbeddingService embeddingService;
    private final ConversationService conversationService;
    private final ChatRequestLogService chatRequestLogService;
    private final MediaFileService mediaFileService;
    private final ResourceLoaderFactory resourceLoaderFactory;
    private final WebSearchEngine webSearchEngine;
    private final VectorizationFactory vectorizationFactory;

    @SneakyThrows
    @PostMapping(path = "send")
    @ResponseBody
    public ResultData<List<String>> send(HttpServletRequest httpServletRequest, String sessionId, String content,String kid,String sid,Boolean useLk,Boolean useHistory,Boolean useDocMode,String docId,Boolean useNetMode) {
        SseEmitter sseEmitter = sseCache.get(sessionId);
        List<String> nearestList = new ArrayList<>();
        if (sseEmitter != null) {
            SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
//            ChatRequestLog chatRequestLog = new ChatRequestLog();
//            chatRequestLog.setUserId(sysUser.getId());
//            chatRequestLog.setKid(kid);
//            chatRequestLog.setRequestTime(LocalDateTime.now());
//            chatRequestLog.setContent(content);
//            chatRequestLog.setCreateTime(LocalDateTime.now());
//            chatRequestLogService.save(chatRequestLog);
            List<Conversation> history = new ArrayList<>();
            if (useHistory){
                history = conversationService.history(sid);
            }
            CustomChatMessage customChatMessage = new CustomChatMessage(content,sid);
            // 本地知识库模式
            if (useLk){
                if (vectorizationFactory.getEmbedding() instanceof LocalAiVectorization){
                    // 使用 weaviate向量数据库内置的嵌入向量模型
                    nearestList = vectorStore.nearest(content,kid);
                }else {
                    // 使用外部的嵌入向量模型
                    List<Double> queryVector = embeddingService.getQueryVector(content);
                    nearestList = vectorStore.nearest(queryVector,kid);
                }
                log.info("知识库向量检索结果为{}",nearestList);
            }
            // 文档模式
            if (useDocMode){
                MediaFile mediaFile = mediaFileService.getByMfid(docId);
                if (StringUtils.isNotBlank(docId)){
                    ResourceLoader resourceLoader = resourceLoaderFactory.getLoaderByFileType(mediaFile.getFileSuffix());
                    File file = new File(mediaFile.getFilePath());
                    InputStream inputStream = new FileInputStream(file);
                    String docContent = resourceLoader.getContent(inputStream);
                    nearestList.add(docContent);
                }
            }
            // 联网模式
            if (useNetMode){
                InputStream inputStream = webSearchEngine.search(content);
                String searchContent = webSearchEngine.load(inputStream);
                nearestList.add(searchContent);
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
            if (vectorizationFactory.getEmbedding() instanceof LocalAiVectorization){
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

    @PostMapping(path = "createImageChat")
    @ResponseBody
    public ResultData<CreateImageChatResponse> createImageChat(HttpServletRequest httpServletRequest, @RequestBody ImageChatRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        List<MediaFile> medias = new ArrayList<>();
        List<String> mediaIds = new ArrayList<>();
        List<String> base64List = null;
        int questionCid = conversationService.saveConversation(sysUser.getId(),request.getSid(), request.getContent(), "Q");
        if (CollectionUtil.isEmpty(request.getMediaIds())){
            base64List = imageChatService.createImage(request.getContent());
        }else {
            List<MediaFile> mediaFileList = mediaFileService.listByMfids(request.getMediaIds());
            mediaFileService.updateWithCid(request.getMediaIds(),questionCid);
            List<String> httpUrls = mediaFileList.stream().map((item)->item.getHttpUrl()).collect(Collectors.toList());
            base64List = imageChatService.editImage(request.getContent(), httpUrls);
        }
        for (String base64Image : base64List){
            MediaFileBase64UploadRequest mediaFileBase64UploadRequest = new MediaFileBase64UploadRequest();
            mediaFileBase64UploadRequest.setBase64Image(base64Image);
            MediaFile mediaFile = mediaFileService.base64Upload(mediaFileBase64UploadRequest);
            medias.add(mediaFile);
            mediaIds.add(mediaFile.getMfid());
        }
        String textMsg = "图片消息";
        int answerCid = conversationService.saveConversation(sysUser.getId(),request.getSid(), textMsg, "A");
        mediaFileService.updateWithCid(mediaIds,answerCid);
        CreateImageChatResponse response = new CreateImageChatResponse();
        response.setTextMsg(textMsg);
        response.setMediaFileList(medias);
        return ResultData.success(response,"发送成功");
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
