package com.hkh.sa.base.module.support.accesstoken.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.entity.accesstoken.AccessTokenEntity;
import com.hkh.domain.form.accesstoken.AccessTokenQueryForm;
import com.hkh.domain.vo.accesstoken.AccessTokenVO;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.sa.base.module.support.accesstoken.dao.AccessTokenDao;
import com.hkh.sa.base.module.support.accesstoken.service.AccessTokenService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author huangkh
* @description 针对表【access_token(第三方接口访问token)】的数据库操作Service实现
* @createDate 2023-11-06 20:33:39
*/
@Service
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenDao, AccessTokenEntity> implements AccessTokenService {

    @Resource
    private AccessTokenDao accessTokenDao;

    /**
     * 分页查询
     */
    public PageResult<AccessTokenVO> queryPage(AccessTokenQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<AccessTokenVO> list = accessTokenDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }
}




