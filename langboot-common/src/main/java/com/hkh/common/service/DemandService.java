package com.hkh.common.service;

import com.hkh.domain.domain.Demand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.AgentDemandProposeRequest;

import java.util.List;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service
* @createDate 2023-09-23 20:42:03
*/
public interface DemandService extends IService<Demand> {

    void propose(SysUser sysUser, AgentDemandProposeRequest request);

    Demand saveDemand(SysUser sysUser, String did, String fid, String content);


}
