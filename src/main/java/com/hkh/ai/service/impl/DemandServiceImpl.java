package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.agent.prompt.demand.DemandProposePrompt;
import com.hkh.ai.agent.prompt.demand.function.DemandFuncObj;
import com.hkh.ai.domain.AgentField;
import com.hkh.ai.domain.Demand;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AgentDemandProposeRequest;
import com.hkh.ai.service.AgentFieldService;
import com.hkh.ai.service.CompletionService;
import com.hkh.ai.service.DemandService;
import com.hkh.ai.mapper.DemandMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service实现
* @createDate 2023-09-23 20:42:03
*/
@Service
@AllArgsConstructor
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand>
    implements DemandService{

    private final AgentFieldService agentFieldService;

    private final CompletionService completionService;
    @Override
    public void propose(SysUser sysUser, AgentDemandProposeRequest request) {
        AgentField agentField = agentFieldService.getByFid(request.getFid());
        String content = DemandProposePrompt.prompt(agentField.getFieldName(),request.getContent());
        String resultJsonStr = completionService.function(sysUser, content,"demain_propose","get the roles and the steps of the demand", DemandFuncObj.class);
        System.out.println(resultJsonStr);
    }
}




