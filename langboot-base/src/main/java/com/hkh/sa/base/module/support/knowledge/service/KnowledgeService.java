package com.hkh.sa.base.module.support.knowledge.service;

import java.util.List;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.knowledge.KnowledgeEntity;
import com.hkh.domain.form.knowledge.KnowledgeQueryForm;
import com.hkh.domain.vo.knowledge.KnowledgeVO;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.sa.base.module.support.knowledge.dao.KnowledgeDao;
import com.hkh.sa.base.module.support.knowledge.form.KnowledgeAddForm;
import com.hkh.sa.base.module.support.knowledge.form.KnowledgeUpdateForm;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 知识库 Service
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Service
public class KnowledgeService {

    @Resource
    private KnowledgeDao knowledgeDao;

    /**
     * 分页查询
     */
    public PageResult<KnowledgeVO> queryPage(KnowledgeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<KnowledgeVO> list = knowledgeDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(KnowledgeAddForm addForm) {
        KnowledgeEntity knowledgeEntity = SmartBeanUtil.copy(addForm, KnowledgeEntity.class);
        knowledgeDao.insert(knowledgeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(KnowledgeUpdateForm updateForm) {
        KnowledgeEntity knowledgeEntity = SmartBeanUtil.copy(updateForm, KnowledgeEntity.class);
        knowledgeDao.updateById(knowledgeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        knowledgeDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Integer id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        knowledgeDao.deleteById(id);
        return ResponseDTO.ok();
    }
}
