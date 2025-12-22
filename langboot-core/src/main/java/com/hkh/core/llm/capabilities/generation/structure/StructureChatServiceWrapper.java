package com.hkh.core.llm.capabilities.generation.structure;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
@AllArgsConstructor
public class StructureChatServiceWrapper implements StructureChatService{

    private final StructureChatServiceFactory structureChatServiceFactory;
    @Override
    public JSONObject structureCompletion(String systemContent,String userContent, JSONObject jsonSchema) {
        StructureChatService structureChatService = structureChatServiceFactory.getStructureChatService();
        return structureChatService.structureCompletion(systemContent,userContent, jsonSchema);
    }
}
