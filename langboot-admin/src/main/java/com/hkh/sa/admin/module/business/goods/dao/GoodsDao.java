package com.hkh.sa.admin.module.business.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.GoodsEntity;
import com.hkh.sa.base.module.support.goods.form.GoodsQueryForm;
import com.hkh.domain.vo.goods.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {

    /**
     * 分页 查询商品
     *
     */
    List<GoodsVO> query(Page page, @Param("query") GoodsQueryForm query);

    /**
     * 批量更新删除状态
     */

    void batchUpdateDeleted(@Param("goodsIdList")List<Long> goodsIdList,@Param("deletedFlag")Boolean deletedFlag);
}
