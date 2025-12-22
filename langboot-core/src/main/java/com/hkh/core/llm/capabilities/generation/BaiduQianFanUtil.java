package com.hkh.core.llm.capabilities.generation;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.domain.entity.accesstoken.AccessTokenEntity;
import com.hkh.sa.base.module.support.accesstoken.service.AccessTokenService;
import com.hkh.sa.base.module.support.platform.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
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


    private final AccessTokenService accessTokenService;

    private final PlatformService platformService;

    public BaiduQianFanUtil(AccessTokenService accessTokenService, PlatformService platformService) {
        this.accessTokenService = accessTokenService;
        this.platformService = platformService;
    }


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
        QueryWrapper<AccessTokenEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app","baidu_big_model");
        // 提前 1分钟 失效
        queryWrapper.ge("expired_time",LocalDateTime.now().plusSeconds(60L));
        AccessTokenEntity accessToken = accessTokenService.getOne(queryWrapper,false);
        if (accessToken == null){
            PlatformEntity platform = platformService.getByCode("wenxinyiyan");
            String dbToken = "";
            String appKey = "";
            String secretKey = "";
            if (platform != null){
                dbToken = platform.getKeys();
                String[] keyAndSecret = dbToken.split("__");
                appKey = keyAndSecret[0];
                secretKey = keyAndSecret[1];
            }

            String result = HttpUtil.get(BaiduChatApis.GET_TOKEN + "?grant_type=client_credentials&client_id=" + appKey + "&client_secret=" + secretKey);
            JSONObject jsonObject = JSON.parseObject(result);
            String token = jsonObject.getString("access_token");
            int expires_in_seconds = jsonObject.getIntValue("expires_in");
            AccessTokenEntity newAccessToken = new AccessTokenEntity();
            newAccessToken.setApp("baidu_big_model");
            newAccessToken.setToken(token);
            newAccessToken.setExpiredTime(LocalDateTime.now().plusSeconds(expires_in_seconds));
            newAccessToken.setCreateTime(LocalDateTime.now());
            accessTokenService.save(newAccessToken);
            return token;
        }
        return accessToken.getToken();
    }

    public String getAccessTokenOfAudio(){
        QueryWrapper<AccessTokenEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app","baidu_audio");
        // 提前 1分钟 失效
        queryWrapper.ge("expired_time",LocalDateTime.now().plusSeconds(60L));
        AccessTokenEntity accessToken = accessTokenService.getOne(queryWrapper,false);
        if (accessToken == null){
            PlatformEntity platform = platformService.getByCode("wenxinyiyan");
            String dbToken = "";
            String appKey = "";
            String secretKey = "";
            if (platform != null){
                dbToken = platform.getKeys();
                String[] keyAndSecret = dbToken.split("__");
                appKey = keyAndSecret[2];
                secretKey = keyAndSecret[3];
            }

            String result = HttpUtil.get(BaiduChatApis.GET_TOKEN + "?grant_type=client_credentials&client_id=" + appKey + "&client_secret=" + secretKey);
            JSONObject jsonObject = JSON.parseObject(result);
            String token = jsonObject.getString("access_token");
            int expires_in_seconds = jsonObject.getIntValue("expires_in");
            AccessTokenEntity newAccessToken = new AccessTokenEntity();
            newAccessToken.setApp("baidu_audio");
            newAccessToken.setToken(token);
            newAccessToken.setExpiredTime(LocalDateTime.now().plusSeconds(expires_in_seconds));
            newAccessToken.setCreateTime(LocalDateTime.now());
            accessTokenService.save(newAccessToken);
            return token;
        }
        return accessToken.getToken();
    }
}
