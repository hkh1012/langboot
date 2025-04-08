package com.hkh.agent.action.task.analysis;


import com.hkh.agent.action.task.TaskProcessor;

/**
 * 数据分析处理器
 * @author huangkh
 */
public interface DataAnalysisProcessor extends TaskProcessor {

    void analysis(String target);
}
