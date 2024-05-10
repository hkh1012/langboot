package com.hkh.ai.chain.llm.capabilities.generation.function.zhipu;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.ai.chain.llm.capabilities.generation.function.ChatFunctionObject;
import com.hkh.ai.chain.llm.capabilities.generation.function.FunctionChatService;
import com.hkh.ai.chain.llm.capabilities.generation.function.FunctionCompletionResult;
import com.hkh.ai.chain.llm.capabilities.generation.text.zhipu.BlockCompletionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 智普AI函数调用实现
 * @author huangkh
 */
@Service
@Slf4j
public class ZhipuAiFunctionChatService implements FunctionChatService {

    @Autowired
    private ZhipuAiUtil zhipuAiUtil;

    @Override
    public List<FunctionCompletionResult> functionCompletion(String content, List<ChatFunctionObject> functionObjectList) {
        String accessToken = zhipuAiUtil.getAccessToken();

        JSONArray toolArray = new JSONArray();
        for (ChatFunctionObject functionObject : functionObjectList){
            JSONObject tool = new JSONObject();
            tool.put("type","function");
            tool.put("function",functionObject);
            toolArray.add(tool);
        }

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
        body.put("tools", toolArray);
        body.put("tool_choice","auto");

        String resultStr = HttpRequest.post(ZhipuChatApis.COMPLETION_TEXT)
                .header("Authorization",accessToken)
                .header("content-type","application/json")
                .body(body.toJSONString())
                .execute().body();

        // 返回的是非标json，需要特殊处理。。。
        resultStr = resultStr
                .replaceAll("\\\\\\{","{")
                .replaceAll("\\\\}\"","}")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\"\\{","{")
                .replaceAll("}\"","}");

        BlockCompletionResult result = JSONObject.parseObject(resultStr, BlockCompletionResult.class, JSONReader.Feature.AllowUnQuotedFieldNames);
        List<BlockCompletionResult.BlockCompletionResultChoiceMessageToolCall> tool_calls = result.getChoices().get(0).getMessage().getTool_calls();
        List<FunctionCompletionResult> functionResultList = new ArrayList<>();
        for (int i = 0; i < tool_calls.size(); i++) {
            BlockCompletionResult.BlockCompletionResultChoiceMessageToolCall toolCall = tool_calls.get(i);
            FunctionCompletionResult functionCompletionResult = new FunctionCompletionResult();
            functionCompletionResult.setType("function");
            functionCompletionResult.setName(toolCall.getFunction().getName());
            functionCompletionResult.setArguments(toolCall.getFunction().getArguments());
            functionResultList.add(functionCompletionResult);
        }
        return functionResultList;
    }
}
