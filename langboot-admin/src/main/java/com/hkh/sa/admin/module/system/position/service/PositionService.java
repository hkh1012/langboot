package com.hkh.sa.admin.module.system.position.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.module.system.position.dao.PositionDao;
import com.hkh.domain.entity.PositionEntity;
import com.hkh.domain.form.position.PositionAddForm;
import com.hkh.domain.form.position.PositionQueryForm;
import com.hkh.domain.form.position.PositionUpdateForm;
import com.hkh.domain.vo.position.PositionVO;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职务表 Service
 *
 * @Author kaiyun
 * @Date 2024-06-23 23:31:38
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */

@Service
public class PositionService {

    @Resource
    private PositionDao positionDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PositionVO> queryPage(PositionQueryForm queryForm) {
        queryForm.setDeletedFlag(Boolean.FALSE);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PositionVO> list = positionDao.queryPage(page, queryForm);
        PageResult<PositionVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PositionAddForm addForm) {
        PositionEntity positionEntity = SmartBeanUtil.copy(addForm, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PositionUpdateForm updateForm) {
        PositionEntity positionEntity = SmartBeanUtil.copy(updateForm, PositionEntity.class);
        positionDao.updateById(positionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        positionDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long positionId) {
        if (null == positionId){
            return ResponseDTO.ok();
        }

        positionDao.deleteById(positionId);
        return ResponseDTO.ok();
    }

    /**
     * 分页查询
     *
     * @return
     */
    public List<PositionVO> queryList() {
        List<PositionVO> list = positionDao.queryList(Boolean.FALSE);
        return list;
    }
}
