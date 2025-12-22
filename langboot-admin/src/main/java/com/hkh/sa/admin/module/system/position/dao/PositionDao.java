package com.hkh.sa.admin.module.system.position.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.PositionEntity;
import com.hkh.domain.form.position.PositionQueryForm;
import com.hkh.domain.vo.position.PositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职务表 Dao
 *
 * @Author kaiyun
 * @Date 2024-06-23 23:31:38
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */

@Mapper
public interface PositionDao extends BaseMapper<PositionEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PositionVO> queryPage(Page page, @Param("queryForm") PositionQueryForm queryForm);


    /**
     * 查询
     * @param deletedFlag
     * @return
     */
    List<PositionVO> queryList(@Param("deletedFlag") Boolean deletedFlag);
}
