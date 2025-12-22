package com.hkh.sa.base.module.support.helpdoc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.helpdoc.HelpDocEntity;
import com.hkh.domain.form.helpdoc.HelpDocQueryForm;
import com.hkh.sa.base.module.support.helpdoc.domain.form.HelpDocUpdateForm;
import com.hkh.domain.vo.helpdoc.HelpDocVO;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.sa.base.module.support.helpdoc.dao.HelpDocDao;
import com.hkh.sa.base.module.support.helpdoc.domain.form.HelpDocAddForm;
import com.hkh.sa.base.module.support.helpdoc.domain.vo.HelpDocDetailVO;
import com.hkh.sa.base.module.support.helpdoc.manager.HelpDocManager;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理业务
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-20 23:11:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class HelpDocService {

    @Resource
    private HelpDocDao helpDocDao;

    @Resource
    private HelpDocManager helpDaoManager;


    /**
     * 查询 帮助文档
     *
     * @param queryForm
     * @return
     */
    public PageResult<HelpDocVO> query(HelpDocQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<HelpDocVO> list = helpDocDao.query(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     *
     * @param addForm
     * @return
     */
    public ResponseDTO<String> add(HelpDocAddForm addForm) {
        HelpDocEntity helpDaoEntity = SmartBeanUtil.copy(addForm, HelpDocEntity.class);
        helpDaoManager.save(helpDaoEntity, addForm.getRelationList());
        return ResponseDTO.ok();
    }


    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(HelpDocUpdateForm updateForm) {
        // 更新
        HelpDocEntity helpDaoEntity = SmartBeanUtil.copy(updateForm, HelpDocEntity.class);
        helpDaoManager.update(helpDaoEntity, updateForm.getRelationList());
        return ResponseDTO.ok();
    }


    /**
     * 删除
     *
     * @param helpDocId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long helpDocId) {
        HelpDocEntity helpDaoEntity = helpDocDao.selectById(helpDocId);
        if (helpDaoEntity != null) {
            helpDocDao.deleteById(helpDocId);
            helpDocDao.deleteRelation(helpDocId);
        }
        return ResponseDTO.ok();
    }

    /**
     * 获取详情
     *
     * @param helpDocId
     * @return
     */
    public HelpDocDetailVO getDetail(Long helpDocId) {
        HelpDocEntity helpDaoEntity = helpDocDao.selectById(helpDocId);
        HelpDocDetailVO detail = SmartBeanUtil.copy(helpDaoEntity, HelpDocDetailVO.class);
        if (detail != null) {
            detail.setRelationList(helpDocDao.queryRelationByHelpDoc(helpDocId));
        }
        return detail;
    }

    /**
     * 获取详情
     *
     * @param relationId
     * @return
     */
    public List<HelpDocVO> queryHelpDocByRelationId(Long relationId) {
        return helpDocDao.queryHelpDocByRelationId(relationId);
    }
}
