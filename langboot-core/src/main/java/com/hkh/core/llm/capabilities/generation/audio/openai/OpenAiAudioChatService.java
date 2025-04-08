package com.hkh.core.llm.capabilities.generation.audio.openai;

import com.hkh.core.llm.OpenAiServiceProxy;
import com.hkh.core.llm.capabilities.generation.audio.AudioChatService;
import com.theokanning.openai.audio.CreateSpeechRequest;
import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.audio.TranscriptionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * openai语音聊天服务
 * @author huangkh
 */
@Slf4j
@Service
public class OpenAiAudioChatService implements AudioChatService {

    @Value("${chain.llm.openai.model}")
    private String defaultModel;

//    @Autowired
//    private ConversationService conversationService;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

//    @Autowired
//    private MediaFileService mediaFileService;

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

}
