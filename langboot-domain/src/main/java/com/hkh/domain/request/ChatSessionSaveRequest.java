package com.hkh.domain.request;

import lombok.Data;

@Data
public class ChatSessionSaveRequest {

    private String title;

    private Integer modelId;

    private String sid;

}
