package com.hkh.sa.base.module.support.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.domain.form.platform.PlatformAddForm;
import com.hkh.domain.form.platform.PlatformQueryForm;
import com.hkh.domain.form.platform.PlatformUpdateForm;
import com.hkh.domain.vo.platform.PlatformVO;

import java.util.List;

/**
* @author huangkh
* @description 针对表【platform(平台)】的数据库操作Service
* @createDate 2024-08-30 15:43:30
*/
public interface PlatformService extends IService<PlatformEntity> {

    PlatformEntity getByCode(String code);

    /**
     * 分页查询
     */
     PageResult<PlatformVO> queryPage(PlatformQueryForm queryForm);

    /**
     * 添加
     */
     ResponseDTO<String> add(PlatformAddForm addForm);

    /**
     * 更新
     *
     */
     ResponseDTO<String> update(PlatformUpdateForm updateForm) ;

    /**
     * 批量删除
     */
     ResponseDTO<String> batchDelete(List<Integer> idList);

    /**
     * 单个删除
     */
     ResponseDTO<String> delete(Integer id) ;
}
