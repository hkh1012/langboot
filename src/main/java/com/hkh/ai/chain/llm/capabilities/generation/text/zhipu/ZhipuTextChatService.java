package com.hkh.ai.chain.llm.capabilities.generation.text.zhipu;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.ai.chain.llm.capabilities.generation.text.TextChatService;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ZhipuTextChatService implements TextChatService {

    @Autowired
    private ZhipuAiUtil zhipuAiUtil;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ZhipuCompletionWebClient zhipuCompletionWebClient;

    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser) throws IOException {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(request.getContent());
        System.out.println("promptTokens length == " + promptTokens.size());

        System.out.println("Streaming chat completion...");
        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), request.getContent(), "Q");

        JSONArray messages = new JSONArray();

        // history context
        for (Conversation conversation : history) {
            JSONObject historyJson = new JSONObject();
            historyJson.put("role",conversation.getType().equals("Q") ? "user" : "assistant");
            historyJson.put("content",conversation.getContent());
            messages.add(historyJson);
        }

        // nearest context
        String nearestContext = "";
        if (nearestList!=null && nearestList.size() >0){
            nearestContext = "请根据下面的上下文信息:\n\n";
            for (String nearest : nearestList){
                nearestContext += nearest + ";";
            }
        }

        // 添加问题
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",nearestContext + request.getContent());
        messages.add(jsonObject);

        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("stream",true);
        body.put("model",zhipuAiUtil.getCompletionModel());
        body.put("request_id", UUID.fastUUID().toString(true));
        body.put("temperature",0.95);

        ZhipuCompletionBizProcessor bizProcessor = ZhipuCompletionBizProcessor.builder()
                .conversationService(conversationService)
                .sb(new StringBuilder())
                .sseEmitter(sseEmitter)
                .sysUser(sysUser)
                .request(request)
                .enc(enc)
                .promptTokens(promptTokens)
                .build();

        zhipuCompletionWebClient.createFlux(body, bizProcessor);
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
        httpRequest.header("Authorization",accessToken);
        httpRequest.header("content-type","application/json");
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();

        BlockCompletionResult result = JSONObject.parseObject(resultStr, BlockCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
