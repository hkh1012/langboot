package com.hkh.ai.agent.action.task.notice.sms;

/**
 * 极光短信发送处理器
 * @author huangkh
 */
public class JiGuangSmsSendProcessor implements SmsSendProcessor{
    @Override
    public void process(String target) {
        this.send(target);
    }

    @Override
    public void send(String content) {

    }
}
