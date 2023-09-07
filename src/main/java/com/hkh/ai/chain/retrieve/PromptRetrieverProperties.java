package com.hkh.ai.chain.retrieve;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chain.prompt.retriever")
public class PromptRetrieverProperties {

    /**
     * 严格模式
     * true：只能从知识库中回答，如果知识库中没有找到符合条件的，回答未找到对应答案
     * false：允许模型自由回答问题
     */
    private boolean strict;
    /**
     * 检索策略：
     * best_only：仅最优
     * best_first：最优优先
     */
    private String strategy;
    /**
     * 从知识库中检索的条数，limits 应大于 num
     */
    private int limits;
    /**
     * 最终保留的条数
     */
    private int num;
    /**
     * 最优的确信度阈值
     */
    private float best;
    /**
     * 建议的确信度阈值
     */
    private float suggest;
}
