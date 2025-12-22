package com.hkh.sa.base.module.support.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.domain.form.platform.PlatformAddForm;
import com.hkh.domain.form.platform.PlatformQueryForm;
import com.hkh.domain.form.platform.PlatformUpdateForm;
import com.hkh.domain.vo.platform.PlatformVO;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.sa.base.module.support.platform.dao.PlatformDao;
import com.hkh.sa.base.module.support.platform.service.PlatformService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author huangkh
* @description 针对表【platform(平台)】的数据库操作Service实现
* @createDate 2024-08-30 15:43:30
*/
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformDao, PlatformEntity>
    implements PlatformService {

    @Resource
    private PlatformDao platformDao;

    @Override
    public PlatformEntity getByCode(String code) {
        QueryWrapper<PlatformEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        return getOne(queryWrapper,false);
    }


    /**
     * 分页查询
     */
    @Override
    public PageResult<PlatformVO> queryPage(PlatformQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PlatformVO> list = platformDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    @Override
    public ResponseDTO<String> add(PlatformAddForm addForm) {
        PlatformEntity platformEntity = SmartBeanUtil.copy(addForm, PlatformEntity.class);
        platformDao.insert(platformEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    @Override
    public ResponseDTO<String> update(PlatformUpdateForm updateForm) {
        PlatformEntity platformEntity = SmartBeanUtil.copy(updateForm, PlatformEntity.class);
        platformDao.updateById(platformEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    @Override
    public ResponseDTO<String> batchDelete(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        platformDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    @Override
    public ResponseDTO<String> delete(Integer id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        platformDao.deleteById(id);
        return ResponseDTO.ok();
    }
}




