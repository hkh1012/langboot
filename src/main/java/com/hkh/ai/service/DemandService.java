package com.hkh.ai.service;

import com.hkh.ai.domain.Demand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AgentDemandProposeRequest;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service
* @createDate 2023-09-23 20:42:03
*/
public interface DemandService extends IService<Demand> {

    void propose(SysUser sysUser, AgentDemandProposeRequest request);
}
