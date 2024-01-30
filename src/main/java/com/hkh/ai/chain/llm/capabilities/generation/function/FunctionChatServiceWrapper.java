package com.hkh.ai.chain.llm.capabilities.generation.function;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 函数调用服务接口
 * @author huangkh
 */
@Slf4j
@Service
@Primary
@AllArgsConstructor
public class FunctionChatServiceWrapper implements FunctionChatService{

    private final FunctionChatServiceFactory functionChatServiceFactory;
    @Override
    public List<FunctionCompletionResult> functionCompletion(String content, List<ChatFunctionObject> functionObjectList) {
        FunctionChatService functionChatService = functionChatServiceFactory.getFunctionChatService();
        return functionChatService.functionCompletion(content,functionObjectList);
    }
}
