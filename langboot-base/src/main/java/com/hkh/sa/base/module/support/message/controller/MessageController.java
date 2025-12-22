package com.hkh.sa.base.module.support.message.controller;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.RequestUser;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import com.hkh.domain.form.message.MessageQueryForm;
import com.hkh.domain.vo.message.MessageVO;
import com.hkh.sa.base.module.support.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 消息
 *
 * @author luoyi
 * @date 2024/06/22 20:20
 */
@RestController
@Tag(name = SwaggerTagConst.Support.MESSAGE)
public class MessageController extends SupportBaseController {

    @Resource
    private MessageService messageService;

    @Operation(summary = "分页查询我的消息 @luoyi")
    @PostMapping("/message/queryMyMessage")
    public ResponseDTO<PageResult<MessageVO>> query(@RequestBody @Valid MessageQueryForm queryForm) {
        RequestUser user = SmartRequestUtil.getRequestUser();
        if(user == null){
            return ResponseDTO.userErrorParam("用户未登录");
        }

        queryForm.setSearchCount(false);
        queryForm.setReceiverUserId(user.getUserId());
        queryForm.setReceiverUserType(user.getUserType().getValue());
        return ResponseDTO.ok(messageService.query(queryForm));
    }

    @Operation(summary = "查询未读消息数量 @luoyi")
    @GetMapping("/message/getUnreadCount")
    public ResponseDTO<Long> getUnreadCount() {
        RequestUser user = SmartRequestUtil.getRequestUser();
        if(user == null){
            return ResponseDTO.userErrorParam("用户未登录");
        }
        return ResponseDTO.ok(messageService.getUnreadCount(user.getUserType(), user.getUserId()));
    }

    @Operation(summary = "更新已读 @luoyi")
    @GetMapping("/message/read/{messageId}")
    public ResponseDTO<String> updateReadFlag(@PathVariable("messageId") Long messageId) {
        RequestUser user = SmartRequestUtil.getRequestUser();
        if(user == null){
            return ResponseDTO.userErrorParam("用户未登录");
        }

        messageService.updateReadFlag(messageId, user.getUserType(), user.getUserId());
        return ResponseDTO.ok();
    }

}
