package com.hkh.ai.service;

import com.hkh.ai.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author huangkh
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-06-07 14:40:30
*/
public interface SysUserService extends IService<SysUser> {

    Boolean loginCheck(HttpServletRequest httpServletRequest, String username, String password);

    SysUser getByUsernameAndPassowrd(String username, String password);
}
