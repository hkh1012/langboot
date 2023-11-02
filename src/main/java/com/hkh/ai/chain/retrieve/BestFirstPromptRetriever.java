package com.hkh.ai.chain.retrieve;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.internal.LinkedTreeMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 最优优先：有最优取最优，无最优用建议，取满指定数量为止
 * @author hkh
 */
@Component
@Slf4j
@Primary
@AllArgsConstructor
public class BestFirstPromptRetriever implements PromptRetriever<ArrayList<LinkedTreeMap>>{
    private final PromptRetrieverProperties promptRetrieverProperties;
    @Override
    public ArrayList<LinkedTreeMap> retrieve(ArrayList<LinkedTreeMap> sourceArray) {
        log.info("检索前数据为：{}",sourceArray);
        ArrayList<LinkedTreeMap> resultList = new ArrayList<>();
        for (LinkedTreeMap linkedTreeMap : sourceArray){
            String additionalJsonStr = JSON.toJSONString(linkedTreeMap.get("_additional"));
            JSONObject additionalJson = JSONObject.parseObject(additionalJsonStr);
            float distance = additionalJson.getFloatValue("distance");
            float certainty = 1f - distance / 2f;
            if (certainty >= promptRetrieverProperties.getBest()){
                if (resultList.size()<promptRetrieverProperties.getNum()) {
                    resultList.add(linkedTreeMap);
                }
            }
            if (certainty >= promptRetrieverProperties.getSuggest() && certainty < promptRetrieverProperties.getBest()){
                if (resultList.size()<promptRetrieverProperties.getNum()){
                    resultList.add(linkedTreeMap);
                }
            }
        }
        log.info("检索后数据为：{}",sourceArray);
        return resultList;
    }
}
