package com.hkh.ai.chain.vectorstore;

import java.util.List;

/**
 * 向量存储
 */
public interface VectorStore {
    void storeEmbeddings(List<String> chunkList,List<List<Double>> vectorList, String kid, String docId,Boolean firstTime);
    void removeByDocId(String kid,String docId);
    void removeByKid(String kid);
    List<String> nearest(List<Double> queryVector,String kid);
    List<String> nearest(String query,String kid);

    void storeExampleEmbeddings(List<String> chunkList, List<List<Double>> vectorList, String kid, String docId, Boolean firstTime);

    void removeExampleByKid(String kid);

    List<String> nearestExample(List<Double> queryVector, String kid);
}
