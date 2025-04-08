package com.hkh.common.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.domain.domain.AgentField;
import com.hkh.domain.domain.Demand;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.AgentDemandProposeRequest;
import com.hkh.common.service.AgentFieldService;
import com.hkh.common.service.CompletionService;
import com.hkh.common.service.DemandService;
import com.hkh.common.mapper.DemandMapper;
import com.hkh.common.service.DemandStepService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service实现
* @createDate 2023-09-23 20:42:03
*/
@Service
@AllArgsConstructor
@Slf4j
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService{

    private final AgentFieldService agentFieldService;
    private final CompletionService completionService;
    private final DemandStepService demandStepService;
    @Override
    public void propose(SysUser sysUser, AgentDemandProposeRequest request) {
//        AgentField agentField = agentFieldService.getByFid(request.getFid());
//        String content = DemandProposePrompt.prompt(agentField.getFieldName(),request.getContent());
//        DemandFuncObj demandFuncObj = null;
//        log.info("[AGENT]完成目标需要的角色与步骤: {}",demandFuncObj);
//        Demand demand = saveDemand(sysUser, RandomUtil.randomString(32), request.getFid(), request.getContent());
//        stepRole(sysUser,agentField,demand,demandFuncObj.getRoles(),demandFuncObj.getSteps());
    }

    @Override
    public Demand saveDemand(SysUser sysUser, String did, String fid, String content){
        Demand demand = new Demand();
        demand.setDid(did);
        demand.setFid(fid);
        demand.setContent(content);
        demand.setUserId(sysUser.getId());
        demand.setCreateTime(new Date());
        demand.setUnambiguous(true);
        save(demand);
        return demand;
    }

}




