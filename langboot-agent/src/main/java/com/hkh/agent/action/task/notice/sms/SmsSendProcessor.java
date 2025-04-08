package com.hkh.agent.action.task.notice.sms;


import com.hkh.agent.action.task.TaskProcessor;

/**
 * 短信发送处理器
 * @author huangkh
 */
public interface SmsSendProcessor extends TaskProcessor {

    void send(String content);
}
