package com.hkh.ai.controller;

import cn.hutool.core.io.FileUtil;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.config.SysConfig;
import com.hkh.ai.domain.MediaFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 媒体访问控制器
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("resources")
public class ResourceController {

    private final SysConfig sysConfig;

    @GetMapping(value = "{mediaType}/{dateStr}/{fileName}")
    public ResponseEntity<byte[]> resource(@PathVariable String mediaType, @PathVariable String dateStr, @PathVariable String fileName){
        File file = new File(sysConfig.getUploadPath() + File.separator + mediaType + File.separator + dateStr + File.separator + fileName);
        byte[] bytes = FileUtil.readBytes(file);
        HttpHeaders headers=new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"),"iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ResponseEntity<byte[]> entity = new ResponseEntity(bytes, headers, HttpStatus.OK);
            return entity;
        } catch (UnsupportedEncodingException e) {
            log.error("访问资源文件错误,url=" + mediaType + "/" + dateStr + "/" + fileName,e);
            throw new RuntimeException(e);
        }
    }
}
