package com.hkh.core.llm.capabilities.generation.text.kimi;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.domain.dto.FluxStreamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 百度千帆业务处理器
 * @author huangkh
 */
@Builder
@Data
@AllArgsConstructor
@Slf4j
public class KimiStreamBizProcessor implements KimiBizProcessor{

    private final FluxStreamDto fluxStreamDto;

    @Override
    public void bizProcess(String item){
        System.out.println("Kimi流式输出:" +item);
        if (!"[DONE]".equals(item)){
            StreamCompletionResult resultObj = JSONObject.parseObject(item, StreamCompletionResult.class);
            String content = resultObj.getChoices().get(0).getDelta().getContent();
            if (StringUtils.isNotBlank(resultObj.getChoices().get(0).getFinish_reason())) {
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
