package com.hkh.common.service;

import com.github.pagehelper.PageInfo;
import com.hkh.domain.domain.SpecialNoun;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.SpecialNounPageRequest;
import com.hkh.domain.request.SpecialNounSaveRequest;

import java.util.List;

/**
* @author huangkh
* @description 针对表【special_noun(特殊名词)】的数据库操作Service
* @createDate 2024-01-25 08:50:26
*/
public interface SpecialNounService extends IService<SpecialNoun> {

    void saveOne(SpecialNounSaveRequest requestBody, SysUser sysUser);

    PageInfo<SpecialNoun> pageInfo(SpecialNounPageRequest specialNounPageRequest);

    List<SpecialNoun> listOrderBySort();

    String match(String text);
}
