package com.hkh.ai.controller;

import com.hkh.ai.chain.llm.capabilities.generation.audio.AudioChatService;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.annotation.AdminRequired;
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
 * 专有名称功能
 * @author huangkh
 */
@RestController
@AllArgsConstructor
@RequestMapping("special")
public class SpecialNounController {


    private final SpecialNounService specialNounService;
    /**
     * 保存专有名词
     * @param httpServletRequest
     * @param requestBody
     * @return
     */
    @PostMapping(value = "save")
    @AdminRequired
    public ResultData save(@RequestBody SpecialNounSaveRequest requestBody, HttpServletRequest httpServletRequest){
        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_KEY);
        specialNounService.saveOne(requestBody,sysUser);
        return ResultData.success("保存知识库成功");
    }

    /**
     * 删除专业名词
     * @param requestBody
     * @return
     */
    @PostMapping("remove")
    @AdminRequired
    public ResultData remove(@RequestBody SpecialNounRemoveRequest requestBody){
        specialNounService.removeById(requestBody.getId());
        return ResultData.success("删除知识库成功");
    }

}
