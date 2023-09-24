package com.hkh.ai.agent.action.task.collect.uri;

/**
 * Ftp 请求处理器
 */
public class FtpRequestProcessor implements UriRequestProcessor {
    @Override
    public void process(String target) {
        this.request(target);
    }

    @Override
    public void request(String url) {

    }
}
