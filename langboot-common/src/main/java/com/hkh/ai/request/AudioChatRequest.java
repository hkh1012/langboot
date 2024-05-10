package com.hkh.ai.request;

import lombok.Data;

@Data
public class AudioChatRequest {

    private String sessionId;
    private String mediaId;
    private String content;
    private String kid;
    private String sid;
    private Boolean useLk;
    private Boolean useHistory;

}
