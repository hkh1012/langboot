package com.hkh.ai.request;

import lombok.Data;

@Data
public class AgentDemandProposeRequest {
    /**
     * 所属领域ID
     */
    private String fid;

    /**
     * 内容
     */
    private String content;

}
