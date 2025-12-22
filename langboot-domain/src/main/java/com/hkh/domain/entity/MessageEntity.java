package com.hkh.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hkh.domain.enums.MessageTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息实体
 *
 * @author luoyi
 * @date 2024/06/22 20:20
 */
@Data
@TableName("message")
public class MessageEntity {

    @TableId(type = IdType.AUTO)
    private Long messageId;

    /**
     * 消息类型
     *
     * @see MessageTypeEnum
     */
    private Integer messageType;
    /**
     * 接收者类型
     *
     */
    private Integer receiverUserType;

    /**
     * 接收者id
     */
    private Long receiverUserId;

    /**
     * 相关业务id
     */
    private String dataId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读
     */
    private Boolean readFlag;

    /**
     * 已读时间
     */
    private LocalDateTime readTime;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
