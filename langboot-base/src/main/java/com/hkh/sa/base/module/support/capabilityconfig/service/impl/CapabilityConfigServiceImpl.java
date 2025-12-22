package com.hkh.sa.base.module.support.capabilityconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import com.hkh.sa.base.module.support.capabilityconfig.dao.CapabilityConfigDao;
import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author huangkh
* @description 针对表【capability_config(模型能力配置)】的数据库操作Service实现
* @createDate 2024-08-30 15:44:59
*/
@Service
public class CapabilityConfigServiceImpl extends ServiceImpl<CapabilityConfigDao, CapabilityConfigEntity>
    implements CapabilityConfigService {

    public CapabilityConfigEntity defaultConfig;

    private final CapabilityConfigDao capabilityConfigDao;

    public CapabilityConfigServiceImpl(CapabilityConfigDao capabilityConfigDao) {
        this.capabilityConfigDao = capabilityConfigDao;
    }

    @PostConstruct
    public void init(){
        defaultConfig = getConfig();
    }

    @Override
    public CapabilityConfigEntity getDefaultConfig(){
        return this.defaultConfig;
    }

    @Override
    public void setDefaultConfig(CapabilityConfigEntity config){
        this.defaultConfig = config;
    }

    @Override
    public CapabilityConfigEntity getConfig() {
        List<CapabilityConfigEntity> list = list();
        if (list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveConfig(CapabilityConfigEntity requestBody, String userNo) {
        CapabilityConfigEntity capabilityConfigEntity = getConfig();
        if (capabilityConfigEntity == null){
            requestBody.setCreateBy(userNo);
            requestBody.setCreateTime(LocalDateTime.now());
            capabilityConfigDao.insert(requestBody);
            setDefaultConfig(requestBody);
        }else {
            capabilityConfigEntity.setTextModel(requestBody.getTextModel());
            capabilityConfigEntity.setImageModel(requestBody.getImageModel());
            capabilityConfigEntity.setVisionModel(requestBody.getVisionModel());
            capabilityConfigEntity.setFuncModel(requestBody.getFuncModel());
            capabilityConfigEntity.setJsonModel(requestBody.getJsonModel());
            capabilityConfigEntity.setSpeechModel(requestBody.getSpeechModel());
            capabilityConfigEntity.setOmniModel(requestBody.getOmniModel());
            capabilityConfigEntity.setCreateTime(LocalDateTime.now());
            capabilityConfigEntity.setCreateBy(userNo);
            updateById(capabilityConfigEntity);
            setDefaultConfig(capabilityConfigEntity);
        }
    }
}




