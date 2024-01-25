package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkh.ai.domain.Knowledge;
import com.hkh.ai.domain.SpecialNoun;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.SpecialNounPageRequest;
import com.hkh.ai.request.SpecialNounSaveRequest;
import com.hkh.ai.service.SpecialNounService;
import com.hkh.ai.mapper.SpecialNounMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author huangkh
* @description 针对表【special_noun(特殊名词)】的数据库操作Service实现
* @createDate 2024-01-25 08:50:26
*/
@Service
public class SpecialNounServiceImpl extends ServiceImpl<SpecialNounMapper, SpecialNoun>
    implements SpecialNounService{

    @Override
    public void saveOne(SpecialNounSaveRequest requestBody, SysUser sysUser) {
        if (requestBody.getId() == null || requestBody.getId() == 0){
            SpecialNoun specialNoun = new SpecialNoun();
            specialNoun.setPinyin(requestBody.getPinyin());
            specialNoun.setContent(requestBody.getContent());
            specialNoun.setCreateBy(sysUser.getUserName());
            specialNoun.setCreateTime(LocalDateTime.now());
            save(specialNoun);
        }else {
            SpecialNoun byId = this.getById(requestBody.getId());
            byId.setPinyin(requestBody.getPinyin());
            byId.setContent(requestBody.getContent());
            saveOrUpdate(byId);
        }

    }

    @Override
    public PageInfo<SpecialNoun> pageInfo(SpecialNounPageRequest specialNounPageRequest) {
        QueryWrapper<SpecialNoun> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("pinyin",specialNounPageRequest.getSearchContent())
                .or().like("content",specialNounPageRequest.getSearchContent());
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(specialNounPageRequest.getPageNum(), specialNounPageRequest.getPageSize());
        PageInfo<SpecialNoun> pageInfo = new PageInfo<>(list(queryWrapper));
        return pageInfo;
    }
}




