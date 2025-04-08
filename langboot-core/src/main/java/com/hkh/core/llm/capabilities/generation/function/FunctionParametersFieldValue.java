package com.hkh.core.llm.capabilities.generation.function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionParametersFieldValue {
    private String type;
    private String description;
}
