package com.hkh.ai.agent.action.task.notice.app;

/**
 * 微信通知发送处理器
 * @author huangkh
 */
public class WeiXinSendProcessor implements SocialAppSendProcessor{
    @Override
    public void process(String target) {
        this.send(target);
    }

    @Override
    public void send(String content) {

    }
}
