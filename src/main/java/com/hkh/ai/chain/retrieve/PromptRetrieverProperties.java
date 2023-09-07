package com.hkh.ai.chain.retrieve;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chain.prompt.retriever")
public class PromptRetrieverProperties {

    private String strategy;
    private int num;
    private float best;
    private float suggest;
}
