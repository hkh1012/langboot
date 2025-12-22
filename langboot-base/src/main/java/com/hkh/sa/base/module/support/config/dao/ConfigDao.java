package com.hkh.sa.base.module.support.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.ConfigEntity;
import com.hkh.domain.form.config.ConfigQueryForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统参数配置 sys_config Dao层
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-14 20:46:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Mapper
public interface ConfigDao extends BaseMapper<ConfigEntity> {

    /**
     * 分页查询系统配置
     *
     */
    List<ConfigEntity> queryByPage(Page page, @Param("query") ConfigQueryForm queryForm);

    /**
     * 根据key查询获取数据
     *
     */
    ConfigEntity selectByKey(String key);
}
