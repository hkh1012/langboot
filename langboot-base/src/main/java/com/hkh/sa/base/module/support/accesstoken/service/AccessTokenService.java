package com.hkh.sa.base.module.support.accesstoken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.entity.accesstoken.AccessTokenEntity;
import com.hkh.domain.form.accesstoken.AccessTokenQueryForm;
import com.hkh.domain.vo.accesstoken.AccessTokenVO;

/**
* @author huangkh
* @description 针对表【access_token(第三方接口访问token)】的数据库操作Service
* @createDate 2023-11-06 20:33:39
*/
public interface AccessTokenService extends IService<AccessTokenEntity> {

    PageResult<AccessTokenVO> queryPage(AccessTokenQueryForm queryForm);
}
