package com.hkh.core.llm.capabilities.generation.text.qwen;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.QwenAiUtil;
import com.hkh.core.llm.capabilities.generation.QwenApis;
import com.hkh.core.llm.capabilities.generation.text.TextChatService;
import com.hkh.domain.dto.FluxStreamBuilder;
import com.hkh.domain.dto.FluxStreamDto;
import com.hkh.domain.dto.HistoryMessageDto;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class QwenTextChatService implements TextChatService {

    private final QwenAiUtil qwenAiUtil;
    private final QwenCompletionWebClient qwenCompletionWebClient;

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

        // 添加当前问题
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",content);
        messages.add(jsonObject);

        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("stream",true);
        body.put("model",qwenAiUtil.getCompletionModel());
        body.put("temperature",0.1);

        FluxStreamDto fluxStreamDto = FluxStreamBuilder.build();
        QwenStreamBizProcessor streamBizProcessor = QwenStreamBizProcessor.builder()
                .fluxStreamDto(fluxStreamDto)
                .build();

        qwenCompletionWebClient.createFlux(body, streamBizProcessor);
        return fluxStreamDto;
    }


    @Override
    public String blockCompletion(String content) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("blockCompletion promptTokens length == " + promptTokens.size());
        String appKey = qwenAiUtil.getAppKey();

        // 构建 message
        JSONArray messages = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",content);
        messages.add(jsonObject);

        // 构建请求体
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("model",qwenAiUtil.getCompletionModel());
        body.put("stream",false);
        body.put("temperature",0.1);
        body.put("top_p ",0.1);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(QwenApis.COMPLETION_TEXT));
        httpRequest.method(Method.POST);
        httpRequest.header("Authorization","Bearer " + appKey);
        httpRequest.header("content-type","application/json");
        log.info("qwen blockCompletion body == {}",body.toJSONString());
        httpRequest.body(body.toJSONString());
        HttpResponse response = httpRequest.execute();
        String resultStr = response.body();
        log.info("qwen blockCompletion resultStr == {}",resultStr);

        // 如果请求报错，就存储错误信息和发送信息到文件中
        if (response.getStatus() != 200) {
            saveErrorToFile(messages, resultStr);
        }

        BlockCompletionResult result = JSONObject.parseObject(resultStr, BlockCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }

    private static void saveErrorToFile(JSONArray messages, String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jsonObject.put("request_message", messages);
        jsonObject.put("error_message", errorMessage);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String directoryPath = "logs/aliReqErr";
        String fileName = directoryPath + "/ali_forbidden_wrod_" + date + ".json";

        try {
            // 确保目录存在
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            try (FileWriter file = new FileWriter(fileName, true)) { // true表示追加写入
                file.write(jsonObject.toJSONString() + System.lineSeparator());
            }
        } catch (IOException e) {
            log.error("保存阿里大模型请求体出错 Failed to write to file: ", e);
        }
    }
}
