package com.hkh.ai.service;

import java.util.List;

public interface EmbeddingService {

    void storeEmbeddings(List<String> chunkList, String kid, String docId,Boolean firstTime);

    void removeByDocId(String kid,String docId);

    void removeByKid(String kid);

    List<Double> getQueryVector(String query);
}
