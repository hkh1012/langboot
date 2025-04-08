package com.hkh.user.controller;


import com.hkh.common.service.MediaFileService;
import com.hkh.common.service.SpecialNounService;
import com.hkh.core.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.domain.ResultData;
import com.hkh.domain.constant.SysConstants;
import com.hkh.domain.domain.MediaFile;
import com.hkh.domain.domain.SysUser;
import com.hkh.domain.request.AudioTranscribeRequest;
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
    public ResultData<String> transcribe(HttpServletRequest httpServletRequest, @RequestBody AudioTranscribeRequest request) {
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
