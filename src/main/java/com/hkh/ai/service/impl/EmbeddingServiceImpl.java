package com.hkh.ai.service.impl;

import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorizer.VectorizationFactory;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.chain.vectorstore.VectorStoreFactory;
import com.hkh.ai.service.EmbeddingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final VectorStoreFactory vectorStoreFactory;
    private final VectorizationFactory vectorizationFactory;

    /**
     * 保存向量数据库
     * @param chunkList         文档按行切分的片段
     * @param kid               知识库ID
     * @param docId             文档ID
     */
    @Override
    public void storeEmbeddings(List<String> chunkList, String kid, String docId) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<List<Double>> vectorList = vectorization.batchVectorization(chunkList);
        vectorStore.storeEmbeddings(chunkList,vectorList,kid,docId);
    }

    @Override
    public void removeByDocId(String docId) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.removeByDocId(docId);
    }

    @Override
    public void removeByKid(String kid) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.removeByKid(kid);
    }

    @Override
    public List<Double> getQueryVector(String query) {
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<Double> queryVector = vectorization.singleVectorization(query);
        return queryVector;
    }
}
