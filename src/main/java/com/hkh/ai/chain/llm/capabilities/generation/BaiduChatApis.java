package com.hkh.ai.chain.llm.capabilities.generation;

/**
 * 百度千帆相关 API
 */
public interface BaiduChatApis {

    String ERNIE_BOT = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions";

    String ERNIE_BOT4 = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro";

    String ERNIE_BOT_TURBO = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant";

    String GET_TOKEN = "https://aip.baidubce.com/oauth/2.0/token";

    String EMBEDDING_EMBEDDING_V1 = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/embeddings/embedding-v1";

    String EMBEDDING_BGE_LARGE_ZH = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/embeddings/bge_large_zh";

    String EMBEDDING_BGE_LARGE_EN = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/embeddings/bge_large_en";

}
