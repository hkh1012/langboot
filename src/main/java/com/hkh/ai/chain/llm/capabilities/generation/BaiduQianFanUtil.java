package com.hkh.ai.chain.llm.capabilities.generation;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduChatApis;
import com.hkh.ai.domain.AccessToken;
import com.hkh.ai.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 百度千帆 工具类
 * @author huangkh
 */
@Component
@Slf4j
public class BaiduQianFanUtil {

    @Value("${chain.llm.baidu.model}")
    private String defautModel;

    /**
     * 百度千帆开饭的embedding api model（默认：bge-large-zh）
     * 可选模型：Embedding-V1、bge-large-zh、bge-large-en
     */
    @Value("${chain.vectorization.baidu.model}")
    private String embeddingModel;

    @Value("${chain.llm.baidu.appKey}")
    private String appKey;

    @Value("${chain.llm.baidu.secretKey}")
    private String secretKey;

    @Autowired
    private AccessTokenService accessTokenService;


    public String getUrl(){
        if ("ernie_bot".equals(defautModel)){
            return BaiduChatApis.ERNIE_BOT;
        } else if ("ernie_bot4".equals(defautModel)) {
            return BaiduChatApis.ERNIE_BOT4;
        }else if ("ernie_bot_turbo".equals(defautModel)) {
            return BaiduChatApis.ERNIE_BOT_TURBO;
        }else {
            return BaiduChatApis.ERNIE_BOT;
        }
    }

    public String getEmbeddingUrl(){
        if ("Embedding-V1".equals(embeddingModel)){
            return BaiduChatApis.EMBEDDING_EMBEDDING_V1;
        } else if ("bge-large-zh".equals(embeddingModel)) {
            return BaiduChatApis.EMBEDDING_BGE_LARGE_ZH;
        } else if ("bge-large-zh".equals(embeddingModel)) {
            return BaiduChatApis.EMBEDDING_BGE_LARGE_EN;
        }else {
            return BaiduChatApis.EMBEDDING_BGE_LARGE_ZH;
        }
    }

    public String getAccessToken(){
        QueryWrapper<AccessToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app","baidu");
        // 提前 1分钟 失效
        queryWrapper.ge("expired_time",LocalDateTime.now().plusSeconds(60L));
        AccessToken accessToken = accessTokenService.getOne(queryWrapper,false);
        if (accessToken == null){
            String result = HttpUtil.get(BaiduChatApis.GET_TOKEN + "?grant_type=client_credentials&client_id=" + appKey + "&client_secret=" + secretKey);
            JSONObject jsonObject = JSON.parseObject(result);
            String token = jsonObject.getString("access_token");
            int expires_in_seconds = jsonObject.getIntValue("expires_in");
            AccessToken newAccessToken = new AccessToken();
            newAccessToken.setApp("baidu");
            newAccessToken.setToken(token);
            newAccessToken.setExpiredTime(LocalDateTime.now().plusSeconds(expires_in_seconds));
            newAccessToken.setCreateTime(LocalDateTime.now());
            accessTokenService.save(newAccessToken);
            return token;
        }
        return accessToken.getToken();
    }
}
