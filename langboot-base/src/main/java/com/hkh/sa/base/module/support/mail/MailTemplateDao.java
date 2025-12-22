package com.hkh.sa.base.module.support.mail;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkh.domain.entity.MailTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板
 *
 * @Author 1024创新实验室-创始人兼主任:卓大
 * @Date 2024/8/5
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a> ，Since 2012
 */
@Mapper
public interface MailTemplateDao extends BaseMapper<MailTemplateEntity> {

}
