package com.hkh.ai.agent.action.task.decision;

/**
 * 大模型任务决策器
 * @author huangkh
 */
public class LlmTaskDecisionProcessor implements DecisionProcessor{

    @Override
    public void process(String target) {
        String result = decide(target);
        System.out.println("大模型任务决策结果为:" + result);
    }

    @Override
    public String decide(String content) {
        return null;
    }
}
