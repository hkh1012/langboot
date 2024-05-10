package com.hkh.ai.agent.core;

import com.hkh.ai.domain.DemandStep;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 环境
 * @author huangkh
 */
public class Environment {

    private String demand;

    private String workspace;

    private List<DemandStep> stepList;

}
