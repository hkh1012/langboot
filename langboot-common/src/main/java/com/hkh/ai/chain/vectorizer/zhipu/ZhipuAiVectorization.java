package com.hkh.ai.chain.vectorizer.zhipu;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.ai.chain.vectorizer.Vectorization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
@AllArgsConstructor
public class ZhipuAiVectorization implements Vectorization {

    private final ZhipuAiUtil zhipuAiUtil;
    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        List<List<Double>> resultList = new ArrayList<>();
        String accessToken = zhipuAiUtil.getAccessToken();
        for (int i = 0; i < chunkList.size(); i++) {
            String chunk = chunkList.get(i);

            JSONObject body = new JSONObject();
            body.put("input",chunk);
            body.put("model","embedding-2");

            HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(ZhipuChatApis.EMBEDDING_EMBEDDING_V2));
            httpRequest.header("Authorization",accessToken);
            httpRequest.header("content-type","application/json");
            httpRequest.method(Method.POST);
            httpRequest.body(body.toJSONString());
            String resultStr = httpRequest.execute().body();

            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            List<Double> embedding = jsonObject.getJSONArray("data").getJSONObject(0).getList("embedding",Double.class);
            resultList.add(embedding);
        }
        return resultList;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        List<String> chunkList = new ArrayList<>();
        chunkList.add(chunk);
        List<List<Double>> vectorList = batchVectorization(chunkList);
        return vectorList.get(0);
    }
}
