package com.hkh.sa.base.module.support.knowledge.dao;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.knowledge.KnowledgeEntity;
import com.hkh.domain.form.knowledge.KnowledgeQueryForm;
import com.hkh.domain.vo.knowledge.KnowledgeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库 Dao
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Mapper
public interface KnowledgeDao extends BaseMapper<KnowledgeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<KnowledgeVO> queryPage(Page page, @Param("queryForm") KnowledgeQueryForm queryForm);

}
