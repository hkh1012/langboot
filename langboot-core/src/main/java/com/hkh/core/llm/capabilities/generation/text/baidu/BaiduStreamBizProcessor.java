package com.hkh.core.llm.capabilities.generation.text.baidu;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.domain.dto.FluxStreamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 百度千帆业务处理器
 * @author huangkh
 */
@Builder
@Data
@AllArgsConstructor
@Slf4j
public class BaiduStreamBizProcessor implements BaiduBizProcessor{

    private final FluxStreamDto fluxStreamDto;

    @Override
    public void bizProcess(String item){
        log.info("item == {}",item);
        StreamCompletionResult resultObj = JSONObject.parseObject(item, StreamCompletionResult.class);
        String content = resultObj.getResult();
        if (resultObj.getIs_end()) {
            fluxStreamDto.getTextBlockingDeque().offer("[END]");
            fluxStreamDto.getAudioBlockingDeque().offer("[END]");
        } else {
            fluxStreamDto.getSb().append(content);
            fluxStreamDto.getTextBlockingDeque().offer(content);
            fluxStreamDto.getAudioBlockingDeque().offer(content);
        }
    }
}
