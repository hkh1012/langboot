package com.hkh.ai.controller;

import com.hkh.ai.chain.llm.ChatService;
import com.hkh.ai.chain.llm.ChatServiceFactory;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;
import com.hkh.ai.service.CompletionService;
import com.hkh.ai.service.MediaFileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 语音功能
 */
@RestController
@AllArgsConstructor
@RequestMapping("audio")
public class AudioController {

    private final ChatServiceFactory chatServiceFactory;
    private final MediaFileService mediaFileService;

    /**
     * 音频转文本
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/transcribe"})
    public ResultData<String> transcribe(HttpServletRequest httpServletRequest,@RequestBody AudioTranscribeRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        ChatService chatService = chatServiceFactory.getChatService();
        MediaFile mediaFile = mediaFileService.getByMfid(request.getMediaId());
        String filePath = mediaFile.getFilePath();
        File audio = new File(filePath);
        String text = chatService.audioToText(audio,"请用简体中文输出文本");
        return ResultData.success(text,"成功");
    }

}
