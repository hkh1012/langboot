package com.hkh.ai.chain.llm.capabilities.generation.image.baidu;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduChatApis;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduQianFanUtil;
import com.hkh.ai.chain.llm.capabilities.generation.image.ImageChatService;
import com.hkh.ai.chain.llm.capabilities.generation.text.baidu.BlockCompletionResult;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class BaiduImageChatService implements ImageChatService {
    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;
    @Override
    public List<String> createImage(String prompt) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(prompt);
        System.out.println("promptTokens length == " + promptTokens.size());
        String accessToken = baiduQianFanUtil.getAccessToken();

        JSONObject body = new JSONObject();
        body.put("prompt",prompt);
        body.put("size","1024x1024");
        body.put("n",1);

        String jsonStrResult = HttpUtil.post(BaiduChatApis.IMAGE_CREATE + "?access_token=" + accessToken,body.toJSONString());
//        log.info("baidu ai image create result ===> {}",jsonStrResult);
        JSONObject jsonObject = JSONObject.parseObject(jsonStrResult);
        JSONArray dataArr = jsonObject.getJSONArray("data");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < dataArr.size(); i++) {
            JSONObject item = dataArr.getJSONObject(i);
            String b64_image = "data:image/png;base64," + item.getString("b64_image");
            result.add(b64_image);
        }
        return result;
    }

    @Override
    public List<String> editImage(String content, List<String> httpUrls) {
        // 百度API无图片修改相关的API，降级为图片生成
        return createImage(content);
    }
}
