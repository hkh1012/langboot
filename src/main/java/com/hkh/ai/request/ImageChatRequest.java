package com.hkh.ai.request;

import lombok.Data;

import java.util.List;

@Data
public class ImageChatRequest {

    private String sessionId;
    private List<String> mediaIds;
    private String content;
    private String kid;
    private String sid;
    private Boolean useLk;
    private Boolean useHistory;


}
