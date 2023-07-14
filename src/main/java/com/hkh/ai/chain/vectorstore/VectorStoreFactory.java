package com.hkh.ai.chain.vectorstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VectorStoreFactory {

    @Value("${chain.vector.store.type}")
    private String type;

    private final WeaviateVectorStore weaviateVectorStore;

    public VectorStoreFactory(WeaviateVectorStore weaviateVectorStore) {
        this.weaviateVectorStore = weaviateVectorStore;
    }

    public VectorStore getVectorStore(){
        if ("weaviate".equals(type)){
            return weaviateVectorStore;
        }
        return null;
    }
}
