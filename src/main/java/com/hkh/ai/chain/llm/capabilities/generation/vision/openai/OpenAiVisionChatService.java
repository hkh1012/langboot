package com.hkh.ai.chain.llm.capabilities.generation.vision.openai;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.vision.VisionChatService;
import com.hkh.ai.config.SysConfig;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OpenAi 图文多模态实现
 * @author huangkh
 */
@Service
@Slf4j
@AllArgsConstructor
public class OpenAiVisionChatService implements VisionChatService {

    @Value("${chain.llm.openai.token}")
    private String apiToken;

    @Value("${proxy.http.baseurl}")
    private String baseUrl;

    private final SysConfig sysConfig;

    private WebClient webClient;

    @PostConstruct
    public void init(){
        // 备注：openai-java 库最新版本仍未集成vision api,待新版本集成后需要统一成 OpenAiServiceProxy 的方式
        webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .baseUrl(baseUrl) //设置代理，此处暂时只支持http baseUrl方式
                .build();
    }

    @Override
    public String visionCompletion(String content, List<String> imageUrlList) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());

        JSONObject textJson = new JSONObject();
        textJson.put("type","text");
        textJson.put("text",content);

        JSONArray contentArray = new JSONArray();
        contentArray.add(textJson);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);
        for (String imageUrl : imageUrlList){
            // 如果传的是图片链接，openai无法访问国内的url地址，所有需要将图片转换为base64
            String fileName = System.currentTimeMillis() + ".png";
            File destFile = new File(sysConfig.getUploadPath() + File.separator + "image" + File.separator + dateStr + File.separator + fileName);
            File file = HttpUtil.downloadFileFromUrl(imageUrl, destFile);
            String base64Url = "data:image/png;base64," + Base64.encode(file);

            JSONObject imageUrlJson = new JSONObject();
            imageUrlJson.put("url",base64Url);

            JSONObject imageJson = new JSONObject();
            imageJson.put("type","image_url");
            imageJson.put("image_url",imageUrlJson);
            contentArray.add(imageJson);
        }

        JSONObject visionMessage = new JSONObject();
        visionMessage.put("role","user");
        visionMessage.put("content",contentArray);

        // 构建 message
        JSONArray messages = new JSONArray();
        messages.add(visionMessage);

        // 构建请求体
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("model","glm-4v");
        body.put("request_id", UUID.fastUUID());
        body.put("stream",false);

        ResponseEntity<JSONObject> response = webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiToken)
                .header("content-type", "application/json")
                .bodyValue(body.toJSONString())
                .retrieve()
                .toEntity(JSONObject.class)
                .block();

        JSONObject responseBody = response.getBody();

        ChatCompletionResult result = JSONObject.parseObject(responseBody.toJSONString(), ChatCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
