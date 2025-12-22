package com.hkh.core.llm.capabilities.generation.audio.baidu;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.BaiduChatApis;
import com.hkh.core.llm.capabilities.generation.BaiduQianFanUtil;
import com.hkh.core.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.domain.common.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class BaiduAudioChatService implements AudioChatService {
    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;
    @Autowired
    private SysConfig sysConfig;

    @Override
    public String audioToText(File audio, String prompt) {
        String accessToken = baiduQianFanUtil.getAccessTokenOfAudio();
        String base64 =  Base64.encode(audio);

        JSONObject body = new JSONObject();
        body.put("format","m4a");
        body.put("rate",16000);
        body.put("dev_pid",1537);
        body.put("channel",1);
        body.put("token",accessToken);
        body.put("len",audio.length());
        body.put("speech",base64);
        body.put("cuid","cxjk_baidu_user");

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(BaiduChatApis.AUDI_TO_TEXT));
        httpRequest.header("Accept","application/json");
        httpRequest.header("content-type","application/json");

        httpRequest.method(Method.POST);
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();
        log.info("baidu audio to text result  {}" , resultStr);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        int errNo = jsonObject.getIntValue("err_no");
        String text = "";
        if (errNo == 0){
            JSONArray result = jsonObject.getJSONArray("result");
            for (Object item : result){
                text += item.toString();
            }
        }
        return text;

    }


    /**
     * 百度语音合成
     * 注：百度短语音接口只支持60个汉字，所以只能使用长语音接口
     * 该接口分为两步：1、创建合成语音任务，2、查询任务并下载合成语音文件
     * @param content
     * @param voiceType
     * @return
     */
    @Override
    public InputStream createSpeech(String content, String voiceType) {
        Integer vt = Integer.parseInt(voiceType);
        String accessToken = baiduQianFanUtil.getAccessTokenOfAudio();

        JSONArray textArray = new JSONArray();
        textArray.add(content);
        JSONObject body = new JSONObject();
        body.put("text",textArray);
        body.put("format","mp3-16k");
        body.put("voice",vt);
        body.put("lang","zh");
        //度小宇==1语速偏慢，其他设5
        body.put("speed",vt == 1 ? 6 : 5);
        body.put("pitch",5);
        body.put("volume",5);
        body.put("enable_subtitle",0);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(BaiduChatApis.TEXT_TO_AUDIO + "?access_token=" + accessToken));
        httpRequest.header("Accept","application/json");
        httpRequest.header("content-type","application/json");

        httpRequest.method(Method.POST);
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();
        System.out.println("【百度创建语音任务】" + resultStr);

        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        String taskId = jsonObject.getString("task_id");
        InputStream inputStream = null;
        if (StringUtils.isNotBlank(taskId)){
            while (true){
                HttpRequest taskQueryRequest = new HttpRequest(UrlBuilder.of(BaiduChatApis.AUDIO_TASK_QUERY + "?access_token=" + accessToken));
                taskQueryRequest.header("Accept","application/json");
                taskQueryRequest.header("content-type","application/json");

                JSONArray taskQueryTaskArray = new JSONArray();
                taskQueryTaskArray.add(taskId);
                JSONObject taskQueryBody = new JSONObject();
                taskQueryBody.put("task_ids",taskQueryTaskArray);

                taskQueryRequest.method(Method.POST);
                taskQueryRequest.body(taskQueryBody.toJSONString());
                String taskQueryResult = taskQueryRequest.execute().body();
                System.out.println("【百度查询语音任务结果】" + taskQueryResult);
                JSONObject taskQueryObject = JSONObject.parseObject(taskQueryResult);
                JSONArray taskQueryDataArray = taskQueryObject.getJSONArray("tasks_info");
                JSONObject taskQueryData = taskQueryDataArray.getJSONObject(0);
                String status = taskQueryData.getString("task_status");
                if ("Success".equals(status)){
                    String speechUrl = taskQueryData.getJSONObject("task_result").getString("speech_url");
                    String fileName = System.currentTimeMillis() + ".mp3";
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateStr = sdf.format(now);
                    File destFile = new File(sysConfig.getUploadPath() + File.separator + "audio" + File.separator + dateStr + File.separator + fileName);
                    HttpUtil.downloadFileFromUrl(speechUrl, destFile);
                    inputStream = FileUtil.getInputStream(destFile);
                    break;
                } else if ("Failure".equals(status)) {
                    break;
                } else if ("Running".equals(status)){
                    try {
                        // 线程等待1秒后重新查询
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return inputStream;
    }

}
