package com.hkh.sa.base.config;

import com.hkh.sa.base.module.support.heartbeat.core.HeartBeatManager;
import com.hkh.sa.base.module.support.heartbeat.core.IHeartBeatRecordHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 心跳配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2018/10/9 18:47
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Configuration
public class HeartBeatConfig {

    /**
     * 间隔时间
     */
    @Value("${heart-beat.interval-seconds}")
    private Long intervalSeconds;

    @Resource
    private IHeartBeatRecordHandler heartBeatRecordHandler;

    @Bean
    public HeartBeatManager heartBeatManager() {
        return new HeartBeatManager(intervalSeconds * 1000L, heartBeatRecordHandler);
    }


}
