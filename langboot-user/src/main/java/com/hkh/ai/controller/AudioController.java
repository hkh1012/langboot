package com.hkh.ai.controller;

import com.hkh.ai.chain.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.constant.SysConstants;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.*;
import com.hkh.ai.service.MediaFileService;
import com.hkh.ai.service.SpecialNounService;
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

    private final AudioChatService audioChatService;
    private final MediaFileService mediaFileService;
    private final SpecialNounService specialNounService;

    /**
     * 音频转文本
     * @param httpServletRequest
     * @param request
     * @return
     */
    @PostMapping(value = {"/transcribe"})
    public ResultData<String> transcribe(HttpServletRequest httpServletRequest,@RequestBody AudioTranscribeRequest request) {
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        MediaFile mediaFile = mediaFileService.getByMfid(request.getMediaId());
        String filePath = mediaFile.getFilePath();
        File audio = new File(filePath);
        String text = audioChatService.audioToText(audio,"请用简体中文输出文本");
        System.out.println("转换前text==" + text);
        String convertedText = specialNounService.match(text);
        return ResultData.success(convertedText,"成功");
    }

}
