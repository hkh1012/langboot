package com.hkh.ai.agent.action.goal;

import com.hkh.ai.agent.action.task.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 最终目标
 * @author huangkh
 */
@Data
public class Goal {

    /**
     * 目标ID
     */
    private String goalId;

    /**
     * 目标状态
     */
    private int goalStatus;

    /**
     * 目标文本
     */
    private String goalText;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 子任务
     */
    private List<Task> taskList;

}


