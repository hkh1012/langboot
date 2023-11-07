package com.hkh.ai.chain.llm.baiduqianfan;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.ChatService;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    @Override
    public void streamChat(CustomChatMessage request, List<String> nearestList, List<Conversation> history, SseEmitter sseEmitter, SysUser sysUser) {
        String url = baiduQianFanUtil.getUrl();
        String accessToken = baiduQianFanUtil.getAccessToken();
        JSONArray messages = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role","user");
        jsonObject.put("content",request.getContent());
        messages.add(jsonObject);
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("stream",true);
        String jsonStrResult = HttpUtil.post(url + "?access_token=" + accessToken,body.toJSONString());

    }

    @Override
    public String blockCompletion(String content) {
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
