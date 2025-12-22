package com.hkh.sa.base.module.support.capabilityconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;

/**
* @author huangkh
* @description 针对表【capability_config(模型能力配置)】的数据库操作Service
* @createDate 2024-08-30 15:44:59
*/
public interface CapabilityConfigService extends IService<CapabilityConfigEntity> {

    CapabilityConfigEntity getDefaultConfig();

    void setDefaultConfig(CapabilityConfigEntity config);

    CapabilityConfigEntity getConfig();

    void saveConfig(CapabilityConfigEntity requestBody, String userNo);
}
