package com.hkh.domain.form.completion;

import lombok.Data;

@Data
public class CompletionFunctionRequest {

    private String content;

    private String functionName;

    private String description;

    private Class clazz;

}
