package com.hkh.ai.agent.core.function;

/**
 * AI能力类型枚举
 * @author huangkh
 */
public enum AiFunctionType {

    TEXT_GEN("文本生成"),
    IMAGE_GEN("图片生成"),
    VIDEO_GEN("视频生成"),
    ;

    private String type;

    AiFunctionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
