package com.hkh.ai.chain.vectorstore;

import java.util.List;

/**
 * 向量存储
 */
public interface VectorStore {
    void storeEmbeddings(List<String> chunkList,List<List<Double>> vectorList, String kid, String docId);
    void removeByDocId(String docId);
    void removeByKid(String kid);
    List<String> nearest(List<Double> queryVector);

}
