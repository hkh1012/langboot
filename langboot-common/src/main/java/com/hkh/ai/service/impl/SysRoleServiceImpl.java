package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.SysRole;
import com.hkh.ai.mapper.SysRoleMapper;
import com.hkh.ai.request.RolePageRequest;
import com.hkh.ai.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2024-03-20 14:40:39
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

    @Override
    public PageInfo<SysRole> pageInfo(RolePageRequest request) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("role_name",request.getSearchContent());
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        PageInfo<SysRole> pageInfo = new PageInfo<>(list(queryWrapper));
        return pageInfo;
    }
}




