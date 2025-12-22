package com.hkh.sa.admin.module.system.message;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.message.MessageQueryForm;
import com.hkh.domain.form.message.MessageSendForm;
import com.hkh.domain.vo.message.MessageVO;
import com.hkh.sa.base.module.support.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 后管 消息路由
 *
 * @author: 卓大
 * @date: 2025/04/09 20:55
 */
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_MESSAGE)
@RestController
public class AdminMessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "通知消息-新建")
    @PostMapping("/message/sendMessages")
    @SaCheckPermission("system:message:send")
    public ResponseDTO<String> sendMessages(@RequestBody @Valid ValidateList<MessageSendForm> messageList) {
        messageService.sendMessage(messageList);
        return ResponseDTO.ok();
    }

    @Operation(summary = "通知消息-分页查询")
    @PostMapping("/message/query")
    @SaCheckPermission("system:message:query")
    public ResponseDTO<PageResult<MessageVO>> query(@RequestBody @Valid MessageQueryForm queryForm) {
        return ResponseDTO.ok(messageService.query(queryForm));
    }

    @Operation(summary = "通知消息-删除")
    @GetMapping("/message/delete/{messageId}")
    @SaCheckPermission("system:message:delete")
    public ResponseDTO<String> delete(@PathVariable("messageId") Long messageId) {
        return messageService.delete(messageId);
    }

}
