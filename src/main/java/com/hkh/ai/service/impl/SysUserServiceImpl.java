package com.hkh.ai.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.SysUserService;
import com.hkh.ai.mapper.SysUserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-06-07 14:40:30
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Override
    public Boolean loginCheck(HttpServletRequest httpServletRequest, String username, String password) {
        SysUser sysUser = getByUsernameAndPassowrd(username,password);
        Boolean pass = sysUser != null ? true : false;
        if (pass){
            httpServletRequest.getSession().setAttribute(SysConstants.SESSION_LOGIN_USER_KEY,sysUser);
        }
        return pass;
    }

    @Override
    public SysUser getByUsernameAndPassowrd(String username, String password){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("password", MD5.create().digestHex(password));
        SysUser sysUser = getOne(queryWrapper,false);
        return sysUser;
    }
}




