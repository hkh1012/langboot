package com.hkh.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.core.mapper.AccessTokenMapper;
import com.hkh.core.service.AccessTokenService;
import com.hkh.domain.domain.AccessToken;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【access_token(第三方接口访问token)】的数据库操作Service实现
* @createDate 2023-11-06 20:33:39
*/
@Service
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken>
    implements AccessTokenService {

}




