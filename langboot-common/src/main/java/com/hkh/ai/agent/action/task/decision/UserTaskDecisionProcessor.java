package com.hkh.ai.agent.action.task.decision;

/**
 * 用户任务决策处理器
 * @author huangkh
 */
public class UserTaskDecisionProcessor implements DecisionProcessor{
    @Override
    public void process(String target) {
        String result = decide(target);
        System.out.println("用户决策结果为:" + result);
    }

    @Override
    public String decide(String content) {
        return null;
    }
}
