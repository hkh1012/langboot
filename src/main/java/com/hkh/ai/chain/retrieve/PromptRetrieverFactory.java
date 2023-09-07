package com.hkh.ai.chain.retrieve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 检索器工厂：提供各种策略的检索方法
 * @author hkh
 */
@Component
@Slf4j
public class PromptRetrieverFactory {

    @Value("${chain.prompt.retriever.strategy}")
    private String strategy;

    private final BestOnlyPromptRetriever bestOnlyPromptRetriever;
    private final BestFirstPromptRetriever bestFirstPromptRetriever;

    public PromptRetrieverFactory(BestOnlyPromptRetriever bestOnlyPromptRetriever,
                                  BestFirstPromptRetriever bestFirstPromptRetriever) {
        this.bestOnlyPromptRetriever = bestOnlyPromptRetriever;
        this.bestFirstPromptRetriever = bestFirstPromptRetriever;

    }

    public PromptRetriever getPromptRetriever(){
        if ("best_only".equals(strategy)){
            return bestOnlyPromptRetriever;
        }else if ("best_first".equals(strategy)){
            return bestFirstPromptRetriever;
        }else {
            return null;
        }
    }
}
