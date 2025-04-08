package com.hkh.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.domain.AccessToken;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【access_token(第三方接口访问token)】的数据库操作Mapper
* @createDate 2023-11-06 20:33:39
* @Entity com.hkh.domain.domain.AccessToken
*/
@Mapper
public interface AccessTokenMapper extends BaseMapper<AccessToken> {

}




