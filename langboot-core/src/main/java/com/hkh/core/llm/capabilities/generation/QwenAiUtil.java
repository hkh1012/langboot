package com.hkh.core.llm.capabilities.generation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.domain.entity.accesstoken.AccessTokenEntity;
import com.hkh.sa.base.module.support.accesstoken.service.AccessTokenService;
import com.hkh.sa.base.module.support.platform.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 通义千问 工具类
 * @author majb
 */
@Component
@Slf4j
public class QwenAiUtil {

    @Value("${chain.llm.qwen.model}")
    private String completionModel;

    private final PlatformService platformService;
    private final AccessTokenService accessTokenService;

    public QwenAiUtil(PlatformService platformService, AccessTokenService accessTokenService) {
        this.platformService = platformService;
        this.accessTokenService = accessTokenService;
    }

    public String getCompletionModel(){
        return this.completionModel;
    }

    public String getAppKey(){
        PlatformEntity platform = platformService.getByCode("tongyiqianwen");
        String dbToken = "";
        String appKey = "";
        if (platform != null){
            dbToken = platform.getKeys();
            String[] keys = dbToken.split("__");
            appKey = keys[0];
        }
        return appKey;
    }

    public AccessTokenEntity getAccessTokenOfAudio(){
        QueryWrapper<AccessTokenEntity> queryWrapper = new QueryWrapper<>();
        //阿里智能语音产品、非通义千问产品
        queryWrapper.eq("app","ali_audio");
        // 提前 1分钟 失效
        queryWrapper.ge("expired_time", LocalDateTime.now());
        AccessTokenEntity accessToken = accessTokenService.getOne(queryWrapper,false);
        if (accessToken == null){
            PlatformEntity platform = platformService.getByCode("tongyiqianwen");
            String dbToken;
            String appKey = "";
            String secretKey = "";
            if (platform != null){
                dbToken = platform.getKeys();
                String[] keyAndSecret = dbToken.split("__");
                appKey = keyAndSecret[1];
                secretKey = keyAndSecret[2];
            }

            com.alibaba.nls.client.AccessToken aliToken = new com.alibaba.nls.client.AccessToken(appKey, secretKey);
            try {
                aliToken.apply();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String token = aliToken.getToken();
            long expireTime = aliToken.getExpireTime() * 1000;
            // 时间戳expireTime转为LocalDateTime类型
            Instant instant = Instant.ofEpochMilli(expireTime);
            LocalDateTime expireTimeLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            AccessTokenEntity newAccessToken = new AccessTokenEntity();
            newAccessToken.setApp("ali_audio");
            newAccessToken.setToken(token);
            newAccessToken.setExpiredTime(expireTimeLocalDateTime);
            newAccessToken.setCreateTime(LocalDateTime.now());
            accessTokenService.save(newAccessToken);
            return newAccessToken;
        }
        return accessToken;
    }

}
