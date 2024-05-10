package com.hkh.ai.chain.llm.capabilities.generation.function;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class FunctionCompletionResult {

    private String type;

    private String name;

    private JSONObject arguments;
}
