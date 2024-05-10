package com.hkh.ai.agent.action.task.notice.mail;

import com.hkh.ai.agent.action.task.TaskProcessor;

/**
 * 邮件发送处理器
 * @author huangkh
 */
public interface MailSendProcessor extends TaskProcessor {

    void send(String content);
}
