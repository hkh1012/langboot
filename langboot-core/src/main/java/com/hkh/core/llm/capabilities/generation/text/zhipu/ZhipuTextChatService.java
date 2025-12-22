package com.hkh.core.llm.capabilities.generation.text.zhipu;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.core.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.core.llm.capabilities.generation.text.TextChatService;
import com.hkh.domain.dto.FluxStreamBuilder;
import com.hkh.domain.dto.FluxStreamDto;
import com.hkh.domain.dto.HistoryMessageDto;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZhipuTextChatService implements TextChatService {

    @Autowired
    private ZhipuAiUtil zhipuAiUtil;

    @Autowired
    private ZhipuCompletionWebClient zhipuCompletionWebClient;


    @Override
    public FluxStreamDto stream(String content, String systemPrompt, List<String> nearestList, List<HistoryMessageDto> historyList) {
        JSONArray messages = new JSONArray();
        // 系统提示词
        JSONObject promptJson = new JSONObject();
        StringBuilder nearestContext = new StringBuilder();
        for (String nearest : nearestList){
            nearestContext.append(nearest).append("\n");
        }
        if (!nearestContext.isEmpty()){
            systemPrompt = systemPrompt + "\n\n可供参考的资料如下:\n" + nearestContext;
        }
        if (StrUtil.isNotEmpty(systemPrompt)){
            promptJson.put("role","system");
            promptJson.put("content", systemPrompt);
            messages.add(promptJson);
        }

        // 历史聊天记录
        for (HistoryMessageDto historyMessageDto : historyList) {
            JSONObject historyJson = new JSONObject();
            historyJson.put("role",historyMessageDto.getRole());
            historyJson.put("content",historyMessageDto.getContent());
            messages.add(historyJson);
        }

        // 添加问题
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",content);
        messages.add(jsonObject);

        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("stream",true);
        body.put("model",zhipuAiUtil.getCompletionModel());
        body.put("request_id", UUID.fastUUID().toString(true));
        body.put("temperature",0.95);

        FluxStreamDto fluxStreamDto = FluxStreamBuilder.build();
        ZhipuStreamBizProcessor bizProcessor = ZhipuStreamBizProcessor.builder()
                .fluxStreamDto(fluxStreamDto)
                .build();

        zhipuCompletionWebClient.createFlux(body, bizProcessor);
        return fluxStreamDto;
    }

    @Override
    public String blockCompletion(String content) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());
        String accessToken = zhipuAiUtil.getAccessToken();

        // 构建 message
        JSONArray messages = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",content);
        messages.add(jsonObject);

        // 构建请求体
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("model",zhipuAiUtil.getCompletionModel());
        body.put("request_id", UUID.fastUUID().toString(true));
        body.put("stream",false);
        body.put("temperature",0.95);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(ZhipuChatApis.COMPLETION_TEXT));
        httpRequest.method(Method.POST);
        httpRequest.header("Authorization",accessToken);
        httpRequest.header("content-type","application/json");
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();

        BlockCompletionResult result = JSONObject.parseObject(resultStr, BlockCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
