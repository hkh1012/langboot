package com.hkh.ai.agent.action.task;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * 任务
 */
@Data
public class Task {

    /**
     * 任务Id
     */
    private String taskId;

    /**
     * 任务文本
     */
    private String taskText;

    /**
     * 对应任务处理器
     */
    private TaskProcessor taskProcessor;

    /**
     * 依赖任务列表
     */
    private LinkedHashMap<Task,TaskProcessor> dependTasks;
}
