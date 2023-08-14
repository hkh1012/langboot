package com.hkh.ai.request;

import lombok.Data;

@Data
public class CompletionSummaryRequest {

    private String content;

    private String prompt;

}
