package com.hkh.ai.service;

import com.hkh.ai.agent.prompt.demand.function.DemandRoleFuncObj;
import com.hkh.ai.agent.prompt.demand.function.DemandStepFunObj;
import com.hkh.ai.domain.AgentField;
import com.hkh.ai.domain.Demand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AgentDemandProposeRequest;

import java.util.List;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service
* @createDate 2023-09-23 20:42:03
*/
public interface DemandService extends IService<Demand> {

    void propose(SysUser sysUser, AgentDemandProposeRequest request);

    Demand saveDemand(SysUser sysUser, String did, String fid, String content);

    void stepRole(SysUser sysUser, AgentField agentField, Demand demand, List<DemandRoleFuncObj> roles, List<DemandStepFunObj> steps);

}
