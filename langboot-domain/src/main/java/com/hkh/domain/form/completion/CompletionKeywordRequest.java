package com.hkh.domain.form.completion;

import lombok.Data;

@Data
public class CompletionKeywordRequest {

    private String content;

    private String keywordArea;

    private Integer keywordNum = 3;

}
