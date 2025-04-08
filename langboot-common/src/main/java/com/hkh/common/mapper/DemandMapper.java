package com.hkh.common.mapper;

import com.hkh.domain.domain.Demand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【demand(需求)】的数据库操作Mapper
* @createDate 2023-09-23 20:42:03
* @Entity com.hkh.domain.domain.Demand
*/
@Mapper
public interface DemandMapper extends BaseMapper<Demand> {

}




