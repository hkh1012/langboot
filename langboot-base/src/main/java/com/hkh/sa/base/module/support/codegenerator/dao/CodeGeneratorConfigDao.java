package com.hkh.sa.base.module.support.codegenerator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.entity.CodeGeneratorConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表的 代码生成配置 Dao
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-09-23 20:15:38
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Mapper
public interface CodeGeneratorConfigDao extends BaseMapper<CodeGeneratorConfigEntity> {

}