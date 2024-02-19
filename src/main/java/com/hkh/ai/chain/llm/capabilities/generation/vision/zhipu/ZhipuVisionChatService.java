package com.hkh.ai.chain.llm.capabilities.generation.vision.zhipu;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.ai.chain.llm.capabilities.generation.text.zhipu.BlockCompletionResult;
import com.hkh.ai.chain.llm.capabilities.generation.vision.VisionChatService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 智普AI 图文多模态
 * @author huangkh
 */
@Service
@Slf4j
@AllArgsConstructor
public class ZhipuVisionChatService implements VisionChatService {
    private final ZhipuAiUtil zhipuAiUtil;

    @Override
    public String visionCompletion(String content, List<String> imageUrlList) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());
        String accessToken = zhipuAiUtil.getAccessToken();

        JSONObject textJson = new JSONObject();
        textJson.put("type","text");
        textJson.put("text",content);

        JSONArray contentArray = new JSONArray();
        contentArray.add(textJson);

        for (String imageUrl : imageUrlList){
            JSONObject imageUrlJson = new JSONObject();
//            imageUrlJson.put("url","https://cxjk-static.oss-cn-shanghai.aliyuncs.com/lottery/manoncloud.png");
            imageUrlJson.put("url",imageUrl);

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
        body.put("request_id", UUID.fastUUID().toString(true));
        body.put("stream",false);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(ZhipuChatApis.COMPLETION_TEXT));
        httpRequest.header("Authorization",accessToken);
        httpRequest.header("content-type","application/json");
        httpRequest.method(Method.POST);
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();
        System.out.println(resultStr);

        BlockCompletionResult result = JSONObject.parseObject(resultStr, BlockCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
