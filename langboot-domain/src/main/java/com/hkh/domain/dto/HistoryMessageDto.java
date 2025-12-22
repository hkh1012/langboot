package com.hkh.domain.dto;

import lombok.Data;

/**
 * 历史消息
 */
@Data
public class HistoryMessageDto {
    /**
     * user or assistant
     */
    String role;
    String content;
}
