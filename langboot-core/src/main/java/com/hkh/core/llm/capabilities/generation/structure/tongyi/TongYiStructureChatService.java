package com.hkh.core.llm.capabilities.generation.structure.tongyi;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.QwenAiUtil;
import com.hkh.core.llm.capabilities.generation.QwenApis;
import com.hkh.core.llm.capabilities.generation.structure.StructureChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TongYiStructureChatService implements StructureChatService {

    private final QwenAiUtil qwenAiUtil;

    public TongYiStructureChatService(QwenAiUtil qwenAiUtil) {
        this.qwenAiUtil = qwenAiUtil;
    }

    @Override
    public JSONObject structureCompletion(String systemContent, String userContent, JSONObject jsonSchema) {
        String accessToken = qwenAiUtil.getAppKey();
        System.out.println("accessToken" + accessToken);

        JSONObject body = new JSONObject();
        body.put("model","qwen-turbo");

        JSONArray messageArray = new JSONArray();
        JSONObject systemObject = new JSONObject();
        systemObject.put("role","system");
        systemObject.put("content",systemContent);
        messageArray.add(systemObject);

        JSONObject userObject = new JSONObject();
        userObject.put("role","user");
        userObject.put("content",userContent);
        messageArray.add(userObject);
        body.put("messages",messageArray);

        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type","json_object");
        body.put("response_format",responseFormat);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(QwenApis.JSON_MODEL_URL));
        httpRequest.header("Authorization","Bearer " + accessToken);
        httpRequest.header("content-type","application/json");
        httpRequest.method(Method.POST);
        httpRequest.body(body.toJSONString());
        log.info("tong yi body {}",body.toJSONString());
        String resultStr = httpRequest.execute().body();
        log.info("tong yi structure result {}" , resultStr);
        return JSONObject.parseObject(resultStr);
    }
}
