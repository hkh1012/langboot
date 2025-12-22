package com.hkh.sa.base.module.support.capabilityconfig.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【capability_config(模型能力配置)】的数据库操作Mapper
* @createDate 2024-08-30 15:44:59
* @Entity com.ai.common.domain.CapabilityConfig
*/
@Mapper
public interface CapabilityConfigDao extends BaseMapper<CapabilityConfigEntity> {

}




