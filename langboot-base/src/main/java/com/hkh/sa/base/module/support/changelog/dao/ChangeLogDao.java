package com.hkh.sa.base.module.support.changelog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.ChangeLogEntity;
import com.hkh.domain.form.changelog.ChangeLogQueryForm;
import com.hkh.domain.vo.changelog.ChangeLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统更新日志 Dao
 *
 *
 * @Date 2022-09-26 14:53:50
 * @Copyright 1024创新实验室
 */

@Mapper
public interface ChangeLogDao extends BaseMapper<ChangeLogEntity> {

    /**
     * 分页 查询
     *
     */
    List<ChangeLogVO> queryPage(Page page, @Param("queryForm") ChangeLogQueryForm queryForm);

    /**
     * 根据版本查询 ChangeLog
     *
     */
    ChangeLogEntity selectByVersion(@Param("version") String version);


}
