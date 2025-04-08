package com.hkh.core.vectorizer;

import java.util.List;

/**
 * 向量化
 */
public interface Vectorization {
    List<List<Double>> batchVectorization(List<String> chunkList);
    List<Double> singleVectorization(String chunk);
}
