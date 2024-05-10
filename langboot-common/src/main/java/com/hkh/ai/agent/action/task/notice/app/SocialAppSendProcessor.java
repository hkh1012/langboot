package com.hkh.ai.agent.action.task.notice.app;

import com.hkh.ai.agent.action.task.TaskProcessor;

/**
 * 社交APP通知处理器
 * @author huangkh
 */
public interface SocialAppSendProcessor extends TaskProcessor {

    void send(String content);
}
