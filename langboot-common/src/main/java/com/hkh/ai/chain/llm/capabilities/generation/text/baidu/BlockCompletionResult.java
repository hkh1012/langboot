package com.hkh.ai.chain.llm.capabilities.generation.text.baidu;

import lombok.Data;

@Data
public class BlockCompletionResult {

    private String id;

    private String object;

    private Long created;

    private String result;

    private Boolean is_truncated;

    private Boolean need_clear_history;

    private BlockCompletionResultUsage usage;

    @Data
    class BlockCompletionResultUsage{
        private int prompt_tokens;

        private int completion_tokens;

        private int total_tokens;

    }

}
