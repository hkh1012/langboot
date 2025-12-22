package com.hkh.sa.base.module.support.platform.dao;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.domain.form.platform.PlatformQueryForm;
import com.hkh.domain.vo.platform.PlatformVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 平台 Dao
 *
 * @Author huangkh
 * @Date 2025-06-30 14:50:08
 * @Copyright https://github.com/hkh1012/langboot
 */

@Mapper
public interface PlatformDao extends BaseMapper<PlatformEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PlatformVO> queryPage(Page page, @Param("queryForm") PlatformQueryForm queryForm);

}
