package com.hkh.ai.chain.llm.capabilities.generation.function;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class FunctionParameters {
    private String type = "object";
    private Map<String,FunctionParametersFieldValue> properties;
}
