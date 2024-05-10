package com.hkh.ai.chain.llm.capabilities.generation.function.baidu;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduQianFanUtil;
import com.hkh.ai.chain.llm.capabilities.generation.function.ChatFunctionObject;
import com.hkh.ai.chain.llm.capabilities.generation.function.FunctionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.function.FunctionCompletionResult;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 百度AI函数调用实现
 * @author huangkh
 */
@Service
@Slf4j
@AllArgsConstructor
public class BaiduAiFunctionChatService implements FunctionChatService {
    private final BaiduQianFanUtil baiduQianFanUtil;
    @Override
    public List<FunctionCompletionResult> functionCompletion(String content, List<ChatFunctionObject> functionObjectList) {
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
        body.put("functions",functionObjectList);

        JSONObject tool_choice = new JSONObject();
        tool_choice.put("type","function");
        JSONObject tool_choice_function = new JSONObject();
        tool_choice_function.put("name",functionObjectList.get(0).getName());
        tool_choice.put("function",tool_choice_function);
        body.put("tool_choice",tool_choice);

        String jsonStrResult = HttpUtil.post(url + "?access_token=" + accessToken,body.toJSONString());
        BaiduFunctionCompletionResult result = JSONObject.parseObject(jsonStrResult, BaiduFunctionCompletionResult.class);

        FunctionCompletionResult functionCompletionResult = new FunctionCompletionResult();
        functionCompletionResult.setType("function");
        functionCompletionResult.setName(result.getFunction_call().getString("name"));
        functionCompletionResult.setArguments(result.getFunction_call().getJSONObject("arguments"));

        List<FunctionCompletionResult> functionResultList = new ArrayList<>();
        functionResultList.add(functionCompletionResult);
        return functionResultList;
    }
}
