package com.hkh.ai.agent.input.demand;

import com.hkh.ai.agent.input.UserInputMessage;
import lombok.Data;

/**
 * 需求消息
 */
@Data
public class DemandMessage implements UserInputMessage {

    /**
     * 所属领域：如软件开发领域
     */
    private String field;

    /**
     * 内容
     */
    private String content;

    /**
     * 明确的
     */
    private boolean unambiguous;
}
