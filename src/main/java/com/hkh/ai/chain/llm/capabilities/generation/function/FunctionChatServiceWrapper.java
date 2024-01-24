package com.hkh.ai.chain.llm.capabilities.generation.function;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
    public String functionCompletion(String content, String functionName, String description, Class clazz) {
        FunctionChatService functionChatService = functionChatServiceFactory.getFunctionChatService();
        return functionChatService.functionCompletion(content,functionName,description,clazz);
    }
}
