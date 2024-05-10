package com.hkh.ai.chain.llm.capabilities.generation.image.zhipu;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuChatApis;
import com.hkh.ai.chain.llm.capabilities.generation.image.ImageChatService;
import com.hkh.ai.config.SysConfig;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 智普AI 图文多模态
 * @author huangkh
 */
@Service
@Slf4j
@AllArgsConstructor
public class ZhipuImageChatService implements ImageChatService {
    private final ZhipuAiUtil zhipuAiUtil;
    private final SysConfig sysConfig;

    @Override
    public List<String> createImage(String content) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());
        String accessToken = zhipuAiUtil.getAccessToken();

        // 构建请求体
        JSONObject body = new JSONObject();
        body.put("model","cogview-3");
        body.put("prompt",content);

        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(ZhipuChatApis.COMPLETION_IMAGE));
        httpRequest.header("Authorization",accessToken);
        httpRequest.header("content-type","application/json");
        httpRequest.method(Method.POST);
        httpRequest.body(body.toJSONString());
        String resultStr = httpRequest.execute().body();
        System.out.println(resultStr);

        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        JSONArray data = jsonObject.getJSONArray("data");

        String url = data.getJSONObject(0).getString("url");
        String fileName = System.currentTimeMillis() + ".png";
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);
        File destFile = new File(sysConfig.getUploadPath() + File.separator + "image" + File.separator + dateStr + File.separator + fileName);
        File file = HttpUtil.downloadFileFromUrl(url, destFile);
        String base64Str = "data:image/png;base64," + Base64.encode(file);
        List<String> result = new ArrayList<>();
        result.add(base64Str);
        return result;
    }

    @Override
    public List<String> editImage(String content, List<String> httpUrls) {
        // 智谱无图片编辑相关API,降级为图片生成
        return createImage(content);
    }
}
