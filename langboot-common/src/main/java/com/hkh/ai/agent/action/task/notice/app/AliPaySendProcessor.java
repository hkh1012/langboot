package com.hkh.ai.agent.action.task.notice.app;

/**
 * 支付宝消息发送处理器
 * @author huangkh
 */
public class AliPaySendProcessor implements SocialAppSendProcessor{

    @Override
    public void process(String target) {
        this.send(target);
    }

    @Override
    public void send(String content) {

    }
}
