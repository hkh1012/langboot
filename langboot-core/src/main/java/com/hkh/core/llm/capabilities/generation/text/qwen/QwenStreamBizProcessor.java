package com.hkh.core.llm.capabilities.generation.text.qwen;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.domain.dto.FluxStreamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 通义千问业务处理器
 * @author huangkh
 */
@Builder
@Data
@AllArgsConstructor
@Slf4j
public class QwenStreamBizProcessor implements QwenBizProcessor{

    private final FluxStreamDto fluxStreamDto;

    @Override
    public void bizProcess(String item){
        if (!"[DONE]".equals(item)){
            JSONObject jsonObject = JSONObject.parseObject(item);
            if (jsonObject.containsKey("choices")){
                JSONArray choices = jsonObject.getJSONArray("choices");
                String content = choices.getJSONObject(0).getJSONObject("delta").getString("content");
                if (StringUtils.isNotBlank(choices.getJSONObject(0).getString("finish_reason"))) {
                    fluxStreamDto.getTextBlockingDeque().offer("[END]");
                    fluxStreamDto.getAudioBlockingDeque().offer("[END]");
                } else {
                    fluxStreamDto.getSb().append(content);
                    fluxStreamDto.getTextBlockingDeque().offer(content);
                    fluxStreamDto.getAudioBlockingDeque().offer(content);
                }
            }
        }
    }
}
