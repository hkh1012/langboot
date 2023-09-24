package com.hkh.ai.agent.action.task;

public interface TaskHandler<String,TaskProcessor> {

    void doHandle(String taskId, com.hkh.ai.agent.action.task.TaskProcessor taskProcessor);
}
