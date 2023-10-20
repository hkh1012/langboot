package com.hkh.ai.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.agent.prompt.demand.DemandProposePrompt;
import com.hkh.ai.agent.prompt.demand.DemandStepRolePrompt;
import com.hkh.ai.agent.prompt.demand.functionObj.DemandFuncObj;
import com.hkh.ai.agent.prompt.demand.functionObj.DemandRoleFuncObj;
import com.hkh.ai.agent.prompt.demand.functionObj.DemandStepFunObj;
import com.hkh.ai.agent.prompt.demand.functionObj.StepRoleFuncObj;
import com.hkh.ai.domain.AgentField;
import com.hkh.ai.domain.Demand;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AgentDemandProposeRequest;
import com.hkh.ai.service.AgentFieldService;
import com.hkh.ai.service.CompletionService;
import com.hkh.ai.service.DemandService;
import com.hkh.ai.mapper.DemandMapper;
import com.hkh.ai.service.DemandStepService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        AgentField agentField = agentFieldService.getByFid(request.getFid());
        String content = DemandProposePrompt.prompt(agentField.getFieldName(),request.getContent());
        String resultJsonStr = completionService.function(sysUser, content,"demand_propose","get the roles and the steps of the demand", DemandFuncObj.class);
        System.out.println(resultJsonStr);
        DemandFuncObj demandFuncObj = JSONObject.parseObject(resultJsonStr, DemandFuncObj.class);
        log.info("[AGENT]完成目标需要的角色与步骤: {}",resultJsonStr);
        Demand demand = saveDemand(sysUser, RandomUtil.randomString(32), request.getFid(), request.getContent());
        stepRole(sysUser,agentField,demand,demandFuncObj.getRoles(),demandFuncObj.getSteps());

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

    @Override
    public void stepRole(SysUser sysUser, AgentField agentField, Demand demand, List<DemandRoleFuncObj> roles, List<DemandStepFunObj> steps) {
        for (int i = 0; i < steps.size(); i++) {
            String content = DemandStepRolePrompt.prompt(agentField.getFieldName(),demand.getContent(), StrUtil.join(",",roles),steps.get(i).getDescription());
            String resultJsonStr = completionService.function(sysUser, content,"choose_the_step_role","get the role who finish the demand step", StepRoleFuncObj.class);
            System.out.println(resultJsonStr);
            StepRoleFuncObj stepRoleFuncObj = JSONObject.parseObject(resultJsonStr,StepRoleFuncObj.class);
            log.info("[AGENT]完成步骤{}对应的负责角色为{}",steps.get(i).getStepName(),stepRoleFuncObj.getRoleName());
            demandStepService.saveDemandStep(demand.getDid(),agentField.getFid(),steps.get(i).getStepName(),steps.get(i).getDescription(),stepRoleFuncObj.getRoleName(),sysUser.getId());
        }
    }
}




