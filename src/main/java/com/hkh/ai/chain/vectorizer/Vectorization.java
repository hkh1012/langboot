package com.hkh.ai.chain.vectorizer;

import java.util.List;

/**
 * 向量话
 */
public interface Vectorization {
    List<List<Double>> batchVectorization(List<String> chunkList);
    List<Double> singleVectorization(String chunk);
}
