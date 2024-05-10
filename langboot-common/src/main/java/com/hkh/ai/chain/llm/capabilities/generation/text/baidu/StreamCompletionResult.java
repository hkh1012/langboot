package com.hkh.ai.chain.llm.capabilities.generation.text.baidu;

import lombok.Data;

/**
 * 流式响应结果
 * @author huangkh
 */
@Data
public class StreamCompletionResult {

    private String id;

    private String object;

    private Long created;

    private Integer sentence_id;

    private Boolean is_end;

    private Boolean is_truncated;

    private String result;

    private Boolean need_clear_history;

    private BlockCompletionResultUsage usage;

    @Data
    class BlockCompletionResultUsage{
        private int prompt_tokens;

        private int completion_tokens;

        private int total_tokens;

    }

}
