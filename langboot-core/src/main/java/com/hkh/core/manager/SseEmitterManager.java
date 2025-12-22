package com.hkh.core.manager;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SseEmitterManager {
    //全局单例，sse注册并发容器
    private static final SseEmitterManager instance = new SseEmitterManager(2000);

    private final ConcurrentHashMap<String, SseEmitterWrapper> sseCache;
    private final int maxSize; // 最大容量

    private SseEmitterManager(int maxSize) {
        this.sseCache = new ConcurrentHashMap<>();
        this.maxSize = maxSize;
    }

    public static SseEmitterManager getInstance() {
        return instance;
    }

    // 添加 SseEmitter
    public void addEmitter(String key, SseEmitter emitter) {
        // 如果当前容量超过最大容量，清理最早创建的 SseEmitter
        if (sseCache.size() >= maxSize) {
            cleanupOldestEmitters();
        }
        sseCache.put(key, new SseEmitterWrapper(emitter));
    }

    // 获取 SseEmitter
    public SseEmitter getEmitter(String key) {
        SseEmitterWrapper wrapper = sseCache.get(key);
        return wrapper != null ? wrapper.getEmitter() : null;
    }

    // 移除 SseEmitter
    public void removeEmitter(String key) {
        sseCache.remove(key);
    }

    // 获取所有 SseEmitter
    public Map<String, SseEmitter> getAllEmitters() {
        ConcurrentHashMap<String, SseEmitter> result = new ConcurrentHashMap<>();
        sseCache.forEach((key, wrapper) -> result.put(key, wrapper.getEmitter()));
        return result;
    }

    // 清理最早创建的 SseEmitter
    private void cleanupOldestEmitters() {
        sseCache.entrySet().stream()
                .sorted(Comparator.comparingLong(entry -> entry.getValue().getCreateTime())) // 按创建时间排序
                .limit(sseCache.size() - maxSize + 1) // 清理超过容量的部分
                .forEach(entry -> sseCache.remove(entry.getKey())); // 移除最早创建的 SseEmitter
    }

    // SseEmitter 包装类，用于记录创建时间
    private static class SseEmitterWrapper {
        private final SseEmitter emitter;
        private final long createTime; // 创建时间

        public SseEmitterWrapper(SseEmitter emitter) {
            this.emitter = emitter;
            this.createTime = System.currentTimeMillis(); // 记录创建时间
        }

        public SseEmitter getEmitter() {
            return emitter;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}