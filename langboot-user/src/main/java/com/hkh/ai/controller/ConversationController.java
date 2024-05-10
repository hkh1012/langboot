package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.domain.Conversation;
import com.hkh.ai.request.ChatSessionRemoveRequest;
import com.hkh.ai.chain.service.ConversationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对话
 */
@RestController
@AllArgsConstructor
@RequestMapping("conversation")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping("/list/{sessionId}")
    public ResultData<List<Conversation>> list(@PathVariable(value = "sessionId") String sessionId) {
        List<Conversation> conversationList = conversationService.listBySessionId(sessionId);
        return ResultData.success(conversationList);
    }

    @PostMapping("/remove")
    public ResultData delete(HttpServletRequest httpServletRequest, @RequestBody @Valid ChatSessionRemoveRequest request) {
        conversationService.removeBySid(request.getSid());
        return ResultData.success("删除成功");
    }
}
