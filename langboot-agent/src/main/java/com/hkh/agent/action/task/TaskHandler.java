package com.hkh.agent.action.task;

public interface TaskHandler<String,TaskProcessor> {

    void doHandle(String taskId, TaskProcessor taskProcessor);
}
