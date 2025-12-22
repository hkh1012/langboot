package com.hkh.core.llm.capabilities.generation.audio.tongyi;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.QwenAiUtil;
import com.hkh.core.llm.capabilities.generation.QwenApis;
import com.hkh.core.llm.capabilities.generation.audio.AudioChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Service
@RequiredArgsConstructor
@Slf4j
public class TongYiAudioChatService implements AudioChatService {

    @Value("${ali.tts.appKey}")
    private String apiKey;

    private final QwenAiUtil qwenAiUtil;
    //艾硕
    private static final String VOICE_BOY = "aishuo";
    //思悦
    private static final String VOICE_GIRL = "siyue";


    @Override
    public String audioToText(File audio, String prompt) {
        String vocabularyId = "15638fbcf9ba4d8fac0c138395434c0e";
        String token = qwenAiUtil.getAccessTokenOfAudio().getToken();
        String param = "?appkey=" + apiKey + "&format=mp3&sample_rate=16000&vocabulary_id=" + vocabularyId + "&customization_id=&enable_punctuation_prediction=true&enable_inverse_text_normalization=true&enable_voice_detection=true&disfluency=false";
        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(QwenApis.ONE_SENTENCE_STT_URL + param));
        httpRequest.method(Method.POST);
        httpRequest.header("X-NLS-Token",token);
        httpRequest.header("Content-Length",""+audio.length());
        httpRequest.header("Content-type","application/octet-stream");
        httpRequest.header("Host","nls-gateway-cn-shanghai.aliyuncs.com");
        httpRequest.form("audio",audio);
        String resultStr = httpRequest.execute().body();
        log.info("ali audio to text result  {}" , resultStr);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        int status = jsonObject.getIntValue("status");
        if (status == 20000000){
            return jsonObject.getString("result");
        }
        return "";
    }

    @Override
    public InputStream createSpeech(String content, String voiceType) {
        //如果为空，默认艾硕
        voiceType = StrUtil.isBlank(voiceType) ? VOICE_BOY : voiceType;
        if (StringUtils.isBlank(content)){
            return null;
        }
        Vector<InputStream> streams = new Vector<>();
        List<String> sentenceList = StrUtil.split(content, "。");
        List<String> subSentenceList = new ArrayList<>();
        for (int i = 0; i < sentenceList.size(); i++) {
            String sentence = sentenceList.get(i);
            if (StringUtils.isBlank(sentence)){
                continue;
            }
            List<String> tempSubSentenceList = StrUtil.split(sentence, "，");
            for (int j = 0; j < tempSubSentenceList.size(); j++) {
                if (StringUtils.isBlank(tempSubSentenceList.get(j))){
                    continue;
                }
                subSentenceList.add(tempSubSentenceList.get(j)+"，");
            }
        }
        String tempContent = "";
        for (int i = 0; i < subSentenceList.size(); i++) {
            String item = subSentenceList.get(i);
            if ((tempContent + item).length() > 300){
                String token = qwenAiUtil.getAccessTokenOfAudio().getToken();
                HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(QwenApis.SHORT_TEXT_TTS_URL));
                httpRequest.method(Method.POST);

                JSONObject body = new JSONObject();
                body.put("text",tempContent);
                body.put("appkey",apiKey);
                body.put("token",token);
                body.put("format","mp3");
                body.put("sample_rate","16000");
                body.put("voice",voiceType);
                body.put("volume",50);
                //语速：思悦-150，艾硕-240，其他0
                body.put("speech_rate", VOICE_GIRL.equals(voiceType) ? -150 : VOICE_BOY.equals(voiceType) ? -240 : 0);
                body.put("pitch_rate",0);

                httpRequest.header("X-NLS-Token",token);
                httpRequest.header("Content-Type","application/json");
                HttpResponse response = httpRequest.body(body.toJSONString()).execute();
                String contentType = response.header("Content-Type");
                if ("audio/mpeg".equals(contentType)) {
                    InputStream inputStream = response.bodyStream();
                    streams.add(inputStream);
                    tempContent = "";
                }
            }else {
                tempContent = tempContent + item;
            }
        }

        // 最后一个
        if (!StringUtils.isBlank(tempContent)){
            String token = qwenAiUtil.getAccessTokenOfAudio().getToken();
            HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(QwenApis.SHORT_TEXT_TTS_URL));
            httpRequest.method(Method.POST);

            JSONObject body = new JSONObject();
            body.put("text",tempContent);
            body.put("appkey",apiKey);
            body.put("token",token);
            body.put("format","mp3");
            body.put("sample_rate","16000");
            body.put("voice",voiceType);
            body.put("volume",50);
            body.put("speech_rate",0);
            body.put("pitch_rate",0);

            httpRequest.header("X-NLS-Token",token);
            httpRequest.header("Content-Type","application/json");
            HttpResponse response = httpRequest.body(body.toJSONString()).execute();
            String contentType = response.header("Content-Type");
            if ("audio/mpeg".equals(contentType)) {
                InputStream inputStream = response.bodyStream();
                streams.add(inputStream);
            }
        }

        return new SequenceInputStream(streams.elements());
    }
}
