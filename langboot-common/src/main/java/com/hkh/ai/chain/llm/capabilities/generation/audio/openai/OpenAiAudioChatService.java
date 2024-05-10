package com.hkh.ai.chain.llm.capabilities.generation.audio.openai;

import cn.hutool.core.util.StrUtil;
import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.hkh.ai.chain.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.chain.service.ConversationService;
import com.hkh.ai.service.MediaFileService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.audio.CreateSpeechRequest;
import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.audio.TranscriptionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * openai语音聊天服务
 * @author huangkh
 */
@Slf4j
@Service
public class OpenAiAudioChatService implements AudioChatService {

    @Value("${chain.llm.openai.model}")
    private String defaultModel;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @Autowired
    private MediaFileService mediaFileService;

    @Override
    public String audioToText(File audio,String prompt) {
        OpenAiService service = openAiServiceProxy.service();
        CreateTranscriptionRequest createTranscriptionRequest = CreateTranscriptionRequest
                .builder()
                .model("whisper-1")
                .responseFormat("json")
                .language("zh")
                .prompt(prompt)
                .temperature(0.2d)
                .build();
        TranscriptionResult result = service.createTranscription(createTranscriptionRequest, audio);
        return result.getText();
    }

    @Override
    public InputStream createSpeech(String content) {
        OpenAiService service = openAiServiceProxy.service();
        CreateSpeechRequest createSpeechRequest = CreateSpeechRequest.builder()
                .model("tts-1")
                .input(content)
                .voice("onyx")
                .build();
        final ResponseBody speech = service.createSpeech(createSpeechRequest);
        InputStream inputStream = speech.byteStream();
        return inputStream;
    }

    @Override
    public void audioChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser, String mediaId) {
        OpenAiService service = openAiServiceProxy.service();
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(request.getContent());

        final List<ChatMessage> messages = new ArrayList<>();
        // 保存用户提问
        int questionCid = conversationService.saveConversation(sysUser.getId(),request.getSessionId(), request.getContent(), "Q");
        MediaFile mediaFile = mediaFileService.getByMfid(mediaId);
        mediaFile.setCid(questionCid);
        mediaFileService.saveOrUpdate(mediaFile);

        // 预保存AI回答
        int answerCid = conversationService.saveConversation(sysUser.getId(),request.getSessionId(), "", "A");

        for (String content : nearestList) {
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), content);
            messages.add(systemMessage);
        }
        String ask = request.getContent();
        String temp = "";
        for (Conversation conversation : history){
            temp = temp + conversation.getContent();
        }
        ask = temp + ask;
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), ask + (nearestList.size() > 0 ? "\n\n注意：回答问题时，须严格根据我给你的系统上下文内容原文进行回答，请不要自己发挥,回答时保持原来文本的段落层级" : "" ));
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(defaultModel)
                .messages(messages)
                .temperature(0.1)
                .user(request.getSessionId())
                .n(1)
                .logitBias(new HashMap<>())
                .build();
        StringBuilder sb = new StringBuilder();

        // 创建一个阻塞队列用于存放语音文本片段
        LinkedBlockingDeque<OpenAiAudioChatService.SseAudioChunk> queue = new LinkedBlockingDeque();
        // 创建一个线程用于监听队列的元素
        new Thread(() -> {
            while (true) {
                try {
                    // 从队列中取出元素
                    OpenAiAudioChatService.SseAudioChunk audioChunk = queue.take();
                    sseChunkAudio(audioChunk,answerCid);
                    if (audioChunk.getLastFlag()){
                        // 如果是最后一个元素线程退出
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 切分辅助变量，分别为批号,开始索引,结束索引,每个音频文本大致长度,chunk索引值
        final int[] splitArr = {1,0,0,50,-1};
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(item -> {
                    if (StrUtil.isBlank(item.getChoices().get(0).getFinishReason())
                            && StrUtil.isBlank(item.getChoices().get(0).getMessage().getRole())){
                        String content = item.getChoices().get(0).getMessage().getContent();
                        if (content.endsWith("\n") || content.endsWith("\r")){
                            content = content.replaceAll("\n","<br>");
                            content = content.replaceAll("\r","<br>");
                        }
                        if (content.contains(" ")){
                            content = content.replaceAll(" ","&nbsp;");
                        }
                        sb.append(content);
                        sseEmitter.send(content);
                        splitArr[2] = sb.length();
                        if (sb.length() >= splitArr[3] * splitArr[0]){
                            if (content.endsWith("<br>")){
                                splitArr[4]++;
                                splitArr[0]++;
                                String chunk = sb.substring(splitArr[1], splitArr[2]);
                                queue.offer(new OpenAiAudioChatService.SseAudioChunk(splitArr[4],chunk,sseEmitter,false));
                                splitArr[1] = sb.length();
                            }
                        }
                    }else if (StrUtil.isNotBlank(item.getChoices().get(0).getFinishReason())){
                        splitArr[2] = sb.length();
                        splitArr[4]++;
                        String chunk = sb.substring(splitArr[1], splitArr[2]);
                        queue.offer(new OpenAiAudioChatService.SseAudioChunk(splitArr[4],chunk,sseEmitter,true));

                        sseEmitter.send("[END]");
                        String fullContent = sb.toString();
                        List<Integer> completionToken = enc.encode(fullContent);
                        Conversation byId = conversationService.getById(answerCid);
                        byId.setContent(sb.toString());
                        conversationService.saveOrUpdate(byId);
                    }
                });
        service.shutdownExecutor();
    }

    @Data
    @AllArgsConstructor
    class SseAudioChunk{
        private int index;
        private String chunk;
        private SseEmitter sseEmitter;
        private Boolean lastFlag;
    }
    private void sseChunkAudio(OpenAiAudioChatService.SseAudioChunk sseAudioChunk, int cid) throws IOException {
        String audioChunk = sseAudioChunk.getChunk().replaceAll("<br>","");
        InputStream speech = createSpeech(audioChunk);
        MediaFile mediaFile = mediaFileService.saveFile(speech,cid);
        String prefix = "[sub-audio]";
        if (sseAudioChunk.getLastFlag() && sseAudioChunk.getIndex() == 0){
            // 仅一个chunk
            prefix = "[all-audio]";
        }else if (sseAudioChunk.getLastFlag() && sseAudioChunk.getIndex() != 0){
            // 多个chunk,最后一个
            prefix = "[end-audio]";
        }else if (!sseAudioChunk.getLastFlag() && sseAudioChunk.getIndex() == 0){
            // 多个chunk,第一个
            prefix = "[fst-audio]";
        }
        sseAudioChunk.getSseEmitter().send(prefix + mediaFile.getHttpUrl());
    }

}
