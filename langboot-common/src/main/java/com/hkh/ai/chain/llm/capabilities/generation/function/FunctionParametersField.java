package com.hkh.ai.chain.llm.capabilities.generation.function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionParametersField {
    private String name;
    private String type;
    private String description;
}
