package com.hkh.ai.chain.vectorizer.local;

import com.hkh.ai.chain.vectorizer.Vectorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本地向量化（这里指 weaviate 内置的向量模型）
 * 说明：文本保存到向量库时自动生成文本的向量，所有方法的返回结果为 null
 */
@Component
@Slf4j
@Deprecated
public class LocalAiVectorization implements Vectorization {
    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        return null;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        return null;
    }
}
