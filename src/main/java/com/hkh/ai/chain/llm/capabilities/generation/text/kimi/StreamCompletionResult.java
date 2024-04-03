package com.hkh.ai.chain.llm.capabilities.generation.text.kimi;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class StreamCompletionResult {

    private String id;

    private Long created;

    private List<StreamCompletionResultChoice> choices;

    private StreamCompletionResultUsage usage;

    @Data
    class StreamCompletionResultUsage{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }

    @Data
    class StreamCompletionResultChoice{
        private int index;
        private String finish_reason;

        private StreamCompletionResultChoiceDelta delta;

        @Data
        class StreamCompletionResultChoiceDelta{
            private String role;
            private String content;

            private List<StreamCompletionResultChoiceDeltaToolCall> tool_calls;

            @Data
            class StreamCompletionResultChoiceDeltaToolCall{
                private String id;
                private String type;

                private StreamCompletionResultChoiceMessageToolCallFunction function;

                @Data
                class StreamCompletionResultChoiceMessageToolCallFunction{
                    private String name;
                    private JSONObject arguments;
                }

            }
        }
    }

}
