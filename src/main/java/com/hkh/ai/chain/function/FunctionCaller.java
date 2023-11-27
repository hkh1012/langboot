package com.hkh.ai.chain.function;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.domain.function.DatePeriod;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class FunctionCaller {

    /**
     * 查询天气API
     * @param jsonNode
     * @return
     */
    public String get_location_weather(JSONObject jsonNode){
        String location = jsonNode.getString("location");
        String datePeriod = jsonNode.getString("datePeriod");
        String valueByName = DatePeriod.getValueByName(datePeriod);
        String result = "通过查询(模拟)天气API得到:" +location + valueByName + "天气为晴天";
        log.info(result);
        return result;
    }
}
