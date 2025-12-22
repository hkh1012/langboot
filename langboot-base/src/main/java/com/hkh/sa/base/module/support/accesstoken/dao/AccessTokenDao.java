package com.hkh.sa.base.module.support.accesstoken.dao;


import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.accesstoken.AccessTokenEntity;
import com.hkh.domain.form.accesstoken.AccessTokenQueryForm;
import com.hkh.domain.vo.accesstoken.AccessTokenVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 第三方接口访问token Dao
 *
 * @Author huangkh
 * @Date 2025-06-27 09:39:20
 * @Copyright https://github.com/hkh1012/langboot
 */

@Mapper
public interface AccessTokenDao extends BaseMapper<AccessTokenEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<AccessTokenVO> queryPage(Page page, @Param("queryForm") AccessTokenQueryForm queryForm);

}
