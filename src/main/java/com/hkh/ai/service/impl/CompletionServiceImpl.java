package com.hkh.ai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hkh.ai.chain.llm.ChatService;
import com.hkh.ai.chain.llm.ChatServiceFactory;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.CompletionKeywordRequest;
import com.hkh.ai.request.CompletionSummaryRequest;
import com.hkh.ai.request.CompletionTranslateRequest;
import com.hkh.ai.service.CompletionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CompletionServiceImpl implements CompletionService {

    private final ChatServiceFactory chatServiceFactory;

    @Override
    public String summary(SysUser sysUser, CompletionSummaryRequest request) {
        String fullContent = "请将文本内容生成对应摘要： " + request.getContent() + "\n\n";
        if (StrUtil.isNotBlank(request.getPrompt())){
            fullContent += "具体要求如下：" + request.getPrompt();
        }
        ChatService chatService = chatServiceFactory.getChatService();
        String completionResult = chatService.blockCompletion(fullContent);
        return completionResult;
    }

    @Override
    public String keyword(SysUser sysUser, CompletionKeywordRequest request) {
        String fullContent = "从下面文本中提取" +request.getKeywordNum() +"个"+request.getKeywordArea()+"领域关键词： " + request.getContent() + "\n\n每个关键词以英文分号;分隔";
        ChatService chatService = chatServiceFactory.getChatService();
        String completionResult = chatService.blockCompletion(fullContent);
        return completionResult;
    }

    @Override
    public String translate(SysUser sysUser, CompletionTranslateRequest request) {
        String fullContent = "将下面文本翻译成" +request.getTargetLanguage() + ":" + request.getContent() ;
        ChatService chatService = chatServiceFactory.getChatService();
        String completionResult = chatService.blockCompletion(fullContent);
        return completionResult;
    }
}
