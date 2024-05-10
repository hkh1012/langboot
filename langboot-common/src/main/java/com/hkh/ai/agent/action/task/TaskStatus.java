package com.hkh.ai.agent.action.task;

/**
 * 任务状态
 * @author huangkh
 */
public interface TaskStatus {

    /**
     * 等待中
     */
    public static final int WAITING = 0;

    /**
     * 思考中
     */
    public static final int THINKING = 1;

    /**
     * 计划中
     */
    public static final int PLANNING = 2;

    /**
     * 执行中
     */
    public static final int EXECUTING = 3;

    /**
     * 完成
     */
    public static final int FINISH = 4;

    /**
     * 评估中
     */
    public static final int EVALUATING = 5;

    /**
     * 成功
     */
    public static final int SUCCESS = 6;

    /**
     * 失败
     */
    public static final int FAIL = 7;
}
