package com.hkh.ai.agent.action.task.notice.mail;

/**
 * 网易邮箱发送处理器
 * @author huangkh
 */
public class NetEaseMailSendProcessor implements MailSendProcessor{
    @Override
    public void process(String target) {
        this.send(target);
    }

    @Override
    public void send(String content) {

    }
}
