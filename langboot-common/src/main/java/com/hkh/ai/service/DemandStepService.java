package com.hkh.ai.service;

import com.hkh.ai.domain.DemandStep;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author huangkh
* @description 针对表【demand_step(需求步骤)】的数据库操作Service
* @createDate 2023-09-26 23:46:14
*/
public interface DemandStepService extends IService<DemandStep> {

    DemandStep saveDemandStep(String did, String fid, String stepName, String description, String roleName, Integer userId);
}
