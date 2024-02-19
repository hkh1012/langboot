package com.hkh.ai.chain.llm.capabilities.generation;

import cn.hutool.http.HttpUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkh.ai.domain.AccessToken;
import com.hkh.ai.service.AccessTokenService;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;

/**
 * 智普AI 工具类
 * @author huangkh
 */
@Component
@Slf4j
public class ZhipuAiUtil {

    @Value("${chain.llm.zhipu.model}")
    private String completionModel;

    /**
     * 可选模型：embedding-2
     *
     */
    @Value("${chain.vectorization.zhipu.model}")
    private String embeddingModel;

    @Value("${zhipu.ai.token}")
    private String appKey;

    @Autowired
    private AccessTokenService accessTokenService;


    public String getCompletionModel(){
        return this.completionModel;
    }

    public String getEmbeddingModel(){
        return this.embeddingModel;
    }

    public String getAccessToken(){
        QueryWrapper<AccessToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app","zhipu");
        // 提前 1分钟 失效
        queryWrapper.ge("expired_time",LocalDateTime.now().plusSeconds(60L));
        AccessToken accessToken = accessTokenService.getOne(queryWrapper,false);
        if (accessToken == null){
            String[] keys = appKey.split("\\.");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredTime = now.plusSeconds(60 * 60 * 24);

            Map<String,String> jwtHeader = new HashMap<>();
            jwtHeader.put("alg","HS256");
            jwtHeader.put("sign_type","SIGN");

            Map<String,Object> payloads = new HashMap<>();
            payloads.put("api_key",keys[0]);
            payloads.put("exp",expiredTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            payloads.put("timestamp",now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

            JWT jwt = JWT.create()
                    .addHeaders(jwtHeader)
                    .addPayloads(payloads)
                    .setSigner(JWTSignerUtil.createSigner("HS256", keys[1].getBytes()));
            String jwtToken = jwt.sign();

            AccessToken newAccessToken = new AccessToken();
            newAccessToken.setApp("zhipu");
            newAccessToken.setToken(jwtToken);
            newAccessToken.setExpiredTime(expiredTime);
            newAccessToken.setCreateTime(now);
            accessTokenService.save(newAccessToken);
            return jwtToken;
        }
        return accessToken.getToken();
    }
}
