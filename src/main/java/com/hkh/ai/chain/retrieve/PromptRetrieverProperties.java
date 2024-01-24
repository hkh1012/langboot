package com.hkh.ai.chain.retrieve;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chain.prompt.retriever")
public class PromptRetrieverProperties {
    /**
     * 从知识库中检索的条数，limits 应大于 num
     */
    private int limits;

}
