package com.hkh.domain.request;

import lombok.Data;

@Data
public class CompletionFunctionRequest {

    private String content;

    private String functionName;

    private String description;

    private Class clazz;

}
