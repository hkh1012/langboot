package com.hkh.ai.chain.llm.capabilities.generation.function;

/**
 * 函数调用服务接口
 * @author huangkh
 */
public interface FunctionChatService {
    /**
     * 函数调用：输出结构化数据
     * @param content
     * @return
     */
    String functionCompletion(String content, String functionName,String description ,Class clazz);
}
