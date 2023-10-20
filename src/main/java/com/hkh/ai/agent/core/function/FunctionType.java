package com.hkh.ai.agent.core.function;

/**
 * 能力类型枚举
 * @author huangkh
 */
public enum FunctionType {

    TOOL("工具"),
    AI("AI");

    private String type;

    FunctionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
