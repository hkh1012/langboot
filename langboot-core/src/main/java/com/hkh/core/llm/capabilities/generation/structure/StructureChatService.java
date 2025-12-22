package com.hkh.core.llm.capabilities.generation.structure;

import com.alibaba.fastjson2.JSONObject;

public interface StructureChatService {

    JSONObject structureCompletion(String systemContent,String userContent,JSONObject jsonSchema);
}
