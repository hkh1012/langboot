package com.hkh.ai.agent.action.task.decision;

import com.hkh.ai.agent.action.task.TaskProcessor;

/**
 * 决策处理器
 * @author huangkh
 */
public interface DecisionProcessor extends TaskProcessor {

    String decide(String content);
}
