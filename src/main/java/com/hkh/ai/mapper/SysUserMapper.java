package com.hkh.ai.mapper;

import com.hkh.ai.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-06-07 14:40:30
* @Entity com.hkh.openai.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




