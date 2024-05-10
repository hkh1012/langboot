package com.hkh.ai.chain.llm.capabilities.generation.function.baidu;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class BaiduFunctionCompletionResult {

    private String id;

    private String object;

    private Long created;

    private String result;

    private Boolean is_truncated;

    private Boolean need_clear_history;

    private JSONObject function_call;

    private BlockCompletionResultUsage usage;

    @Data
    class BlockCompletionResultUsage{
        private int prompt_tokens;

        private int completion_tokens;

        private int total_tokens;

    }

}
