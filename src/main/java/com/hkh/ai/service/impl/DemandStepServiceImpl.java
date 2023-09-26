package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.domain.DemandStep;
import com.hkh.ai.service.DemandStepService;
import com.hkh.ai.mapper.DemandStepMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author huangkh
* @description 针对表【demand_step(需求步骤)】的数据库操作Service实现
* @createDate 2023-09-26 23:46:14
*/
@Service
public class DemandStepServiceImpl extends ServiceImpl<DemandStepMapper, DemandStep>
    implements DemandStepService{

    @Override
    public DemandStep saveDemandStep(String did, String fid, String stepName, String description, String roleName, Integer userId) {
        DemandStep demandStep = new DemandStep();
        demandStep.setDid(did);
        demandStep.setFid(fid);
        demandStep.setStepName(stepName);
        demandStep.setDescription(description);
        demandStep.setRole(roleName);
        demandStep.setUserId(userId);
        demandStep.setCreateTime(new Date());
        this.save(demandStep);
        return demandStep;
    }
}




