package com.hkh.sa.base.module.support.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.entity.MessageEntity;
import com.hkh.domain.form.message.MessageQueryForm;
import com.hkh.domain.vo.message.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息 接受者类型枚举
 *
 * @author luoyi
 * @date 2024/06/22 20:20
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {

    /**
     * 分页查询消息
     *
     */
    List<MessageVO> query(Page<?> page, @Param("query") MessageQueryForm queryForm);

    /**
     * 更新已读状态
     */
    Integer updateReadFlag(@Param("messageId") Long messageId,
                           @Param("receiverUserType") Integer receiverUserType,
                           @Param("receiverUserId") Long receiverUserId,
                           @Param("readFlag") Boolean readFlag);

    /**
     * 查询未读消息数
     */
    Long getUnreadCount( @Param("receiverUserType") Integer receiverUserType,
                         @Param("receiverUserId") Long receiverUserId);



}
