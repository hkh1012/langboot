package com.hkh.ai.agent.action.task.notice.mail;

/**
 * QQ邮件发送处理器
 * @author huangkh
 */
public class QqMailSendProcessor implements MailSendProcessor{
    @Override
    public void process(String target) {
        this.send(target);
    }

    @Override
    public void send(String content) {

    }
}
