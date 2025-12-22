package com.hkh.sa.base.module.support.reload.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.entity.reload.ReloadResultEntity;
import com.hkh.domain.vo.reload.ReloadResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * reload_result 数据表dao
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2015-03-02 19:11:52
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Mapper
public interface ReloadResultDao extends BaseMapper<ReloadResultEntity> {

    List<ReloadResultVO> query(@Param("tag") String tag);
}
