package com.hkh.ai.service;

import java.util.List;

public interface EmbeddingService {

    void storeEmbeddings(List<String> chunkList, String kid, String docId);

    void removeByDocId(String docId);

    void removeByKid(String kid);

    List<Double> getQueryVector(String query);
}
