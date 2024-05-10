package com.hkh.ai.mapper;

import com.hkh.ai.domain.DemandStep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【demand_step(需求步骤)】的数据库操作Mapper
* @createDate 2023-09-26 23:46:14
* @Entity com.hkh.ai.domain.DemandStepFunObj
*/
@Mapper
public interface DemandStepMapper extends BaseMapper<DemandStep> {

}




