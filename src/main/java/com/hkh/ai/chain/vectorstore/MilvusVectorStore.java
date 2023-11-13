package com.hkh.ai.chain.vectorstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MilvusVectorStore implements VectorStore{


    @Override
    public void storeEmbeddings(List<String> chunkList, List<List<Double>> vectorList, String kid, String docId, Boolean firstTime) {

    }

    @Override
    public void removeByDocId(String kid, String docId) {

    }

    @Override
    public void removeByKid(String kid) {

    }

    @Override
    public List<String> nearest(List<Double> queryVector, String kid) {
        return null;
    }

    @Override
    public List<String> nearest(String query, String kid) {
        return null;
    }
}
