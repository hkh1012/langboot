package com.hkh.ai.chain.vectorizer;

import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.theokanning.openai.embedding.Embedding;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OpenAiVectorization implements Vectorization {

    @Value("${chain.vectorization.openai.model}")
    private String embeddingModel;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        OpenAiService service = openAiServiceProxy.service();
        EmbeddingRequest embeddingRequest = EmbeddingRequest
                .builder()
                .model(embeddingModel)
                .input(chunkList)
                .build();
        EmbeddingResult embeddings = service.createEmbeddings(embeddingRequest);
        List<List<Double>> vectorList = new ArrayList<>();
        for (Embedding embeddingData : embeddings.getData()){
            List<Double> vector = embeddingData.getEmbedding();
            vectorList.add(vector);
        }
        return vectorList;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        List<String> chunkList = new ArrayList<>();
        chunkList.add(chunk);
        List<List<Double>> vectorList = batchVectorization(chunkList);
        return vectorList.get(0);
    }
}
