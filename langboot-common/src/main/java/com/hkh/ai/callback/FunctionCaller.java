package com.hkh.ai.callback;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class FunctionCaller {

    /**
     * 查询天气API(模拟)
     * @param jsonNode
     * @return
     */
    public String get_location_weather(JSONObject jsonNode){
        String location = jsonNode.getString("location");
        String datePeriod = jsonNode.getString("datePeriod");
//        String valueByName = DatePeriod.getValueByName(datePeriod);
        String result = "(模拟)通过查询天气API得到:" +location + datePeriod + "天气为晴天";
        log.info(result);
        return result;
    }
}
