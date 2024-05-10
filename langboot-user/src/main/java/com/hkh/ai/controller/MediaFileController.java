package com.hkh.ai.controller;

import com.hkh.ai.common.ResultData;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.request.*;
import com.hkh.ai.service.MediaFileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 媒体文件控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("media")
public class MediaFileController {

    private final MediaFileService mediaFileService;

    /**
     * 上传媒体文件
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    public ResultData<MediaFile> upload(MediaFileUploadRequest request){
        MediaFile mediaFile = mediaFileService.upload(request);
        return ResultData.success(mediaFile,"上传媒体文件成功");
    }

    /**
     * 上传base64图片文件
     * @param request
     * @return
     */
    @PostMapping(value = "base64Upload")
    public ResultData<MediaFile> base64Upload(@RequestBody MediaFileBase64UploadRequest request){
        MediaFile mediaFile = mediaFileService.base64Upload(request);
        return ResultData.success(mediaFile,"上传媒体文件成功");
    }
}
