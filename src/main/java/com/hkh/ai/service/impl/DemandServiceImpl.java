package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.domain.Demand;
import com.hkh.ai.service.DemandService;
import com.hkh.ai.mapper.DemandMapper;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Service实现
* @createDate 2023-09-23 20:42:03
*/
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand>
    implements DemandService{

}




