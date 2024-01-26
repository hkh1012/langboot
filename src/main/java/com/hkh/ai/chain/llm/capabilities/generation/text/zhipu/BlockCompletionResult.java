package com.hkh.ai.chain.llm.capabilities.generation.text.zhipu;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class BlockCompletionResult {

    private String id;

    private String model;

    private Long created;

    private List<BlockCompletionResultChoice> choices;

    private BlockCompletionResultUsage usage;

    @Data
    class BlockCompletionResultUsage{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }

    @Data
    class BlockCompletionResultChoice{
        private int index;
        private String finish_reason;

        private BlockCompletionResultChoiceMessage message;

        @Data
        class BlockCompletionResultChoiceMessage{
            private String role;
            private String content;

            private List<BlockCompletionResultChoiceMessageToolCall> tool_calls;

            @Data
            class BlockCompletionResultChoiceMessageToolCall{
                private String id;
                private String type;

                private BlockCompletionResultChoiceMessageToolCallFunction function;

                @Data
                class BlockCompletionResultChoiceMessageToolCallFunction{
                    private String name;
                    private JSONObject arguments;
                }

            }
        }
    }

}
