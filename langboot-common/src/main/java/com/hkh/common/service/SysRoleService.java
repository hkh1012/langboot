package com.hkh.common.service;

import com.github.pagehelper.PageInfo;
import com.hkh.domain.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.request.RolePageRequest;

/**
* @author huangkh
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2024-03-20 14:40:39
*/
public interface SysRoleService extends IService<SysRole> {

    PageInfo<SysRole> pageInfo(RolePageRequest request);
}
