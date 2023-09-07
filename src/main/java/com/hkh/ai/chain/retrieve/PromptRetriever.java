package com.hkh.ai.chain.retrieve;

public interface PromptRetriever<T> {

    T retrieve(T source);
}
