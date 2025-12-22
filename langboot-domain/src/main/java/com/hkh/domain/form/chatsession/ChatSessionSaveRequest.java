package com.hkh.domain.form.chatsession;

import lombok.Data;

@Data
public class ChatSessionSaveRequest {

    private String title;

    private Integer modelId;

    private String sid;

}
