package com.hkh.core.llm.capabilities.generation.text.baidu;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.BaiduQianFanUtil;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 百度千帆API
 * @author huangkh
 */
@Service
@Slf4j
public class BaiduQianFanTextChatService implements TextChatService {

    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;

    @Autowired
    private BaiduCompletionWebClient baiduCompletionWebClient;

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

        List<HistoryMessageDto> newHistoryList = fixHistoryListNew(historyList);
        // 历史聊天记录
        for (HistoryMessageDto historyMessageDto : newHistoryList) {
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

        FluxStreamDto fluxStreamDto = FluxStreamBuilder.build();
        BaiduStreamBizProcessor bizProcessor = BaiduStreamBizProcessor.builder()
                .fluxStreamDto(fluxStreamDto)
                .build();

        baiduCompletionWebClient.createFlux(body, bizProcessor);
        return fluxStreamDto;
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
    private List<HistoryMessageDto> fixHistoryListNew(List<HistoryMessageDto> historyList) {
        List<HistoryMessageDto> newHistoryList = new ArrayList<>();
        for (HistoryMessageDto historyMessageDto : historyList){
            if (newHistoryList.isEmpty()){
                newHistoryList.add(historyMessageDto);
            }else {
                if (!newHistoryList.get(newHistoryList.size()-1).getRole().equals(historyMessageDto.getRole())){
                    newHistoryList.add(historyMessageDto);
                }
            }
        }
        if (!newHistoryList.isEmpty()){
            if (newHistoryList.size() % 2 == 1){
                if (newHistoryList.get(0).getRole().equals("user")){
                    // user -> assistant -> user -> assistant -> user
                    newHistoryList.remove(newHistoryList.size()-1);
                }else {
                    // assistant -> user -> assistant -> user -> assistant
                    newHistoryList.remove(0);
                }
            }else {
                if (newHistoryList.get(0).getRole().equals("assistant")){
                    // assistant -> user -> assistant -> user
                    newHistoryList.remove(newHistoryList.size()-1);
                    newHistoryList.remove(0);
                }
            }
        }
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
        BlockCompletionResult result = JSONObject.parseObject(jsonStrResult, BlockCompletionResult.class);
        return result.getResult();
    }

}
