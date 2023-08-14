package com.hkh.ai.request;

import lombok.Data;

@Data
public class CompletionTranslateRequest {

    private String content;

    private String targetLanguage;

}
