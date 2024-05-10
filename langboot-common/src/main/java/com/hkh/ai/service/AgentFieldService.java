package com.hkh.ai.service;

import com.hkh.ai.domain.AgentField;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author huangkh
* @description 针对表【agent_field(代理领域)】的数据库操作Service
* @createDate 2023-09-23 20:40:24
*/
public interface AgentFieldService extends IService<AgentField> {

    AgentField getByFid(String fid);
}
