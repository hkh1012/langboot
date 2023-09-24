package com.hkh.ai.agent.action.task.collect.uri;

import com.hkh.ai.agent.action.task.TaskProcessor;

/**
 * Uri请求处理器
 */
public interface UriRequestProcessor extends TaskProcessor {

    void request(String url);
}
