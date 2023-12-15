package com.hkh.ai.service.impl;

import com.hkh.ai.chain.vectorizer.Vectorization;
import com.hkh.ai.chain.vectorizer.VectorizationFactory;
import com.hkh.ai.chain.vectorstore.VectorStore;
import com.hkh.ai.chain.vectorstore.VectorStoreFactory;
import com.hkh.ai.service.EmbeddingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void storeEmbeddings(List<String> chunkList, String kid, String docId,List<String> fidList) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<List<Double>> vectorList = vectorization.batchVectorization(chunkList);
        vectorStore.storeEmbeddings(chunkList,vectorList,kid,docId,fidList);
    }

    @Override
    public void removeByDocId(String kid,String docId) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.removeByDocId(kid,docId);
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

    @Override
    public void storeExampleEmbeddings(List<String> chunkList, String kid, String docId, Boolean firstTime) {
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<List<Double>> vectorList = vectorization.batchVectorization(chunkList);
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.storeExampleEmbeddings(chunkList,vectorList,kid,docId,firstTime);
    }

    @Override
    public void removeExampleByKid(String kid) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.removeExampleByKid(kid);
    }

    @Override
    public void createSchema(String kid) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.newSchema(kid);
    }

    @Override
    public void removeByKidAndFid(String kid, String fid) {
        VectorStore vectorStore = vectorStoreFactory.getVectorStore();
        vectorStore.removeByKidAndFid(kid,fid);
    }

    @Override
    public void saveFragment(String kid, String docId, String fid, String content) {
        List<String> chunkList = new ArrayList<>();
        List<String> fidList = new ArrayList<>();
        chunkList.add(content);
        fidList.add(fid);
        storeEmbeddings(chunkList,kid,docId,fidList);
    }
}
