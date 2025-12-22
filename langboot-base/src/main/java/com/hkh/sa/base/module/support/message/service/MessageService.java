package com.hkh.sa.base.module.support.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.enums.base.UserTypeEnum;
import com.hkh.domain.entity.MessageEntity;
import com.hkh.domain.form.message.MessageQueryForm;
import com.hkh.domain.form.message.MessageSendForm;
import com.hkh.domain.form.message.MessageTemplateSendForm;
import com.hkh.domain.vo.message.MessageVO;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.domain.enums.MessageTemplateEnum;
import com.hkh.sa.base.module.support.message.dao.MessageDao;
import jakarta.annotation.Resource;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyi
 * @date 2024/6/27 12:14 上午
 */
@Service
public class MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private MessageManager messageManager;

    /**
     * 分页查询 消息
     */
    public PageResult<MessageVO> query(MessageQueryForm queryForm) {
        Page page = SmartPageUtil.convert2PageQuery(queryForm);
        List<MessageVO> messageVOList = messageDao.query(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, messageVOList);
    }

    /**
     * 查询未读消息数量
     */
    public Long getUnreadCount(UserTypeEnum userType, Long userId) {
        return messageDao.getUnreadCount(userType.getValue(), userId);
    }

    /**
     * 更新已读状态
     */
    public void updateReadFlag(Long messageId, UserTypeEnum userType, Long receiverUserId) {
        messageDao.updateReadFlag(messageId, userType.getValue(), receiverUserId, true);
    }


    /**
     * 发送【模板消息】
     */
    public void sendTemplateMessage(MessageTemplateSendForm... sendTemplateForms) {
        List<MessageSendForm> sendFormList = Lists.newArrayList();
        for (MessageTemplateSendForm sendTemplateForm : sendTemplateForms) {
            MessageTemplateEnum msgTemplateTypeEnum = sendTemplateForm.getMessageTemplateEnum();
            StringSubstitutor stringSubstitutor = new StringSubstitutor(sendTemplateForm.getContentParam());
            String content = stringSubstitutor.replace(msgTemplateTypeEnum.getContent());

            MessageSendForm messageSendForm = new MessageSendForm();
            messageSendForm.setMessageType(msgTemplateTypeEnum.getMessageTypeEnum().getValue());
            messageSendForm.setReceiverUserType(sendTemplateForm.getReceiverUserType().getValue());
            messageSendForm.setReceiverUserId(sendTemplateForm.getReceiverUserId());
            messageSendForm.setTitle(msgTemplateTypeEnum.getDesc());
            messageSendForm.setContent(content);
            messageSendForm.setDataId(sendTemplateForm.getDataId());
            sendFormList.add(messageSendForm);

        }
        this.sendMessage(sendFormList);
    }

    /**
     * 发送消息
     */
    public void sendMessage(MessageSendForm... sendForms) {
        this.sendMessage(Lists.newArrayList(sendForms));
    }

    /**
     * 批量发送通知消息
     */
    public void sendMessage(List<MessageSendForm> sendList) {
        for (MessageSendForm sendDTO : sendList) {
            String verify = SmartBeanUtil.verify(sendDTO);
            if (null != verify) {
                throw new RuntimeException("send msg error: " + verify);
            }
        }
        List<MessageEntity> messageEntityList = sendList.stream().map(e -> {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageType(e.getMessageType());
            messageEntity.setReceiverUserType(e.getReceiverUserType());
            messageEntity.setReceiverUserId(e.getReceiverUserId());
            messageEntity.setDataId(String.valueOf(e.getDataId()));
            messageEntity.setTitle(e.getTitle());
            messageEntity.setContent(e.getContent());
            return messageEntity;
        }).collect(Collectors.toList());
        messageManager.saveBatch(messageEntityList);
    }

    // 删除消息
    public ResponseDTO<String> delete(Long messageId) {
        if(messageId == null){
            return ResponseDTO.userErrorParam();
        }
        messageDao.deleteById(messageId);
        return ResponseDTO.ok();
    }
}