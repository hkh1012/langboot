package com.hkh.ai.chain.vectorizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LocalAiVectorization implements Vectorization {
    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        return null;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        return null;
    }
}
