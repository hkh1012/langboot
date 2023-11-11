package com.hkh.ai.chain.llm.baiduqianfan;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.ChatService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 百度千帆API
 * @author huangkh
 */
@Service
@Slf4j
public class BaiduQianFanChatService implements ChatService {

    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private BaiduQianFanCompletionWebClient baiduQianFanCompletionWebClient;
    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> historyList, SseEmitter sseEmitter, SysUser sysUser) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(request.getContent());
        System.out.println("promptTokens length == " + promptTokens.size());

        System.out.println("Streaming chat completion...");
        conversationService.saveConversation(sysUser.getId(),request.getSessionId(), request.getContent(), "Q");

        JSONArray messages = new JSONArray();

        List<Conversation> newHistoryList = fixHistoryList(historyList);
        // history context
        for (Conversation conversation : newHistoryList) {
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
        BaiduQianFanCompletionBizProcessor bizProcessor = BaiduQianFanCompletionBizProcessor.builder()
                .conversationService(conversationService)
                .sb(new StringBuilder())
                .sseEmitter(sseEmitter)
                .sysUser(sysUser)
                .request(request)
                .enc(enc)
                .promptTokens(promptTokens)
                .build();

        baiduQianFanCompletionWebClient.createFlux(body, bizProcessor);
    }



    /**
     * 适配百度 API 文档 messages 规则
     * 聊天上下文信息。说明：
     * （1）messages成员不能为空，1个成员表示单轮对话，多个成员表示多轮对话
     * （2）最后一个message为当前请求的信息，前面的message为历史对话信息
     * （3）必须为奇数个成员，成员中message的role必须依次为user、assistant(减去用户提问的一个message，所以historyList必须为偶数，且格式必须为 A-->Q-->A-->Q这样的排序形式)
     * （4）最后一个message的content长度（即此轮对话的问题）不能超过3000 token；如果messages中content总长度大于3000 token，系统会依次遗忘最早的历史会话，直到content的总长度不超过3000 token
     * @param historyList
     */
    private List<Conversation> fixHistoryList(List<Conversation> historyList) {
        Collections.reverse(historyList);
        LinkedList<Conversation> linkedList = new LinkedList<>();
        if (historyList!=null && historyList.size() > 0){
            for (int i = 0; i < historyList.size(); i++) {
                Conversation conversation = historyList.get(i);
                if (linkedList.size()==0){
                    if (conversation.getType().equals("A")){
                        linkedList.addFirst(conversation);
                    }
                }else {
                    if (linkedList.size() % 2 == 0) {
                        if (conversation.getType().equals("A")){
                            linkedList.addFirst(conversation);
                        }
                    }else {
                        if (conversation.getType().equals("Q")){
                            linkedList.addFirst(conversation);
                        }
                    }
                }
            }
        }
        if (linkedList.size() > 0 && linkedList.size() % 2 == 1){
            linkedList.removeFirst();
        }
        List<Conversation> newHistoryList = new ArrayList<>();
        newHistoryList.addAll(linkedList);
        return newHistoryList;
    }

    @Override
    public String blockCompletion(String content) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());
        String url = baiduQianFanUtil.getUrl();
        String accessToken = baiduQianFanUtil.getAccessToken();
        JSONArray messages = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",content);
        messages.add(jsonObject);
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        String jsonStrResult = HttpUtil.post(url + "?access_token=" + accessToken,body.toJSONString());
        BlockCompletionResult result = JSONObject.parseObject(jsonStrResult,BlockCompletionResult.class);
        return result.getResult();
    }

    @Override
    public String functionCompletion(String content, String functionName, String description, Class clazz) {
        return null;
    }
}
