package com.hkh.core.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class AudioStreamManager {
    private static volatile Map<String, LinkedBlockingDeque<byte[]>> instance;

    private AudioStreamManager() {
        // 私有构造函数以防止外部实例化
    }

    public static Map<String, LinkedBlockingDeque<byte[]>> getInstance() {
        if (instance == null) {
            synchronized (AudioStreamManager.class) {
                if (instance == null) {
                    instance = new ConcurrentHashMap<>();
                }
            }
        }
        return instance;
    }

    public static LinkedBlockingDeque<byte[]> getIfAbsentStream(String sessionId) {
        return getInstance().computeIfAbsent(sessionId, key -> new LinkedBlockingDeque<>());
    }
}