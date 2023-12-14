package com.hkh.ai.chain.vectorizer;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.baiduqianfan.BaiduQianFanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BaiduQianFanVectorization implements Vectorization{

    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;
    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        List<List<Double>> resultList = new ArrayList<>();
        String url = baiduQianFanUtil.getEmbeddingUrl();
        String accessToken = baiduQianFanUtil.getAccessToken();
        // 百度接口文本数量不能超过16个,所以需要将文本拆分为16个文本一批
        int batchSize = 16;
        for (int i = 0; i < (chunkList.size()-1 / batchSize) + 1; i++) {
            List<String> subList = new ArrayList<>();
            if (chunkList.size() - (batchSize * i) >= batchSize){
                subList = chunkList.subList(i*batchSize,i*batchSize + batchSize);
            }else {
                subList = chunkList.subList(i*batchSize,chunkList.size());
            }

            JSONObject body = new JSONObject();
            body.put("input",subList);
            body.put("user_id", UUID.fastUUID());

            String jsonStrResult = HttpUtil.post(url + "?access_token=" + accessToken,body.toJSONString());
            System.out.println(jsonStrResult);
            BaiduQianFanVectorizationResult baiduQianFanVectorizationResult = JSONObject.parseObject(jsonStrResult, BaiduQianFanVectorizationResult.class);
            List<BaiduQianFanVectorizationResult.BaiuQianFanVectorizationResultItem> embeddings = baiduQianFanVectorizationResult.getData();
            for (BaiduQianFanVectorizationResult.BaiuQianFanVectorizationResultItem item : embeddings){
                List<Double> embedding = item.getEmbedding();
                resultList.add(embedding);
            }
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
