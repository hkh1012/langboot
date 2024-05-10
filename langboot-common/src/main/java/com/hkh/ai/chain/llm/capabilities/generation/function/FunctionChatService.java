package com.hkh.ai.chain.llm.capabilities.generation.function;

import java.util.List;

/**
 * 函数调用服务接口
 * @author huangkh
 */
public interface FunctionChatService {
    /**
     * 函数调用：输出结构化参数数据
     * @param content
     * @return
     */
    List<FunctionCompletionResult> functionCompletion(String content, List<ChatFunctionObject> functionObjectList);
}
