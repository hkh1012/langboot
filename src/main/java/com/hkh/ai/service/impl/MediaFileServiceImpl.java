package com.hkh.ai.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.config.SysConfig;
import com.hkh.ai.domain.MediaFile;
import com.hkh.ai.request.MediaFileBase64UploadRequest;
import com.hkh.ai.request.MediaFileUploadRequest;
import com.hkh.ai.service.MediaFileService;
import com.hkh.ai.mapper.MediaFileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
* @author huangkh
* @description 针对表【media_file(媒体文件)】的数据库操作Service实现
* @createDate 2024-01-11 13:57:30
*/
@Slf4j
@Service
@AllArgsConstructor
public class MediaFileServiceImpl extends ServiceImpl<MediaFileMapper, MediaFile>
    implements MediaFileService{

    private final SysConfig sysConfig;

    @Override
    public MediaFile upload(MediaFileUploadRequest request) {
        String mediaId = StrUtil.uuid();
        MultipartFile multipartFile = request.getFile();
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        String uuidFileName = mediaId + "." + suffix;
        int mediaType = getMediaTypeBySuffix(suffix);
        String mediaTypeStr = getMediaTypeStr(mediaType);
        String subPath =  mediaTypeStr + "/" + DateUtil.format(new Date(),"yyyy-MM-dd");
        String fileSavePath = sysConfig.getUploadPath() + "/" + subPath + "/" + uuidFileName;
        String httpUrl = sysConfig.getResourceDomain() + "/" + subPath + "/" + uuidFileName;

        MediaFile mediaFile = new MediaFile();
        mediaFile.setCid(0);
        mediaFile.setMfid(mediaId);
        mediaFile.setFileName(fileName);
        mediaFile.setFileSuffix(suffix);
        mediaFile.setMediaType(mediaType);
        mediaFile.setFilePath(fileSavePath);
        mediaFile.setFileSize(multipartFile.getSize());
        mediaFile.setHttpUrl(httpUrl);
        mediaFile.setCreateTime(LocalDateTime.now());
        save(mediaFile);
        OutputStream outputStream = null;
        try {
            File dir = new File(sysConfig.getUploadPath() +  File.separator + subPath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            InputStream inputStream = multipartFile.getInputStream();
            outputStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + uuidFileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return mediaFile;
        } catch (IOException e) {
            log.error("文件上传失败。",e);
            throw new RuntimeException(e);
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("文件流关闭失败。",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public MediaFile getByMfid(String mediaId) {
        QueryWrapper<MediaFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mfid",mediaId);
        return getOne(queryWrapper,false);
    }

    @Override
    public MediaFile saveFile(InputStream inputStream, int cid) {
        String mediaId = StrUtil.uuid();
        String fileName = mediaId + ".mp3";
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        int mediaType = getMediaTypeBySuffix(suffix);
        String mediaTypeStr = getMediaTypeStr(mediaType);
        String subPath =  mediaTypeStr + "/" + DateUtil.format(new Date(),"yyyy-MM-dd");
        String fileSavePath = sysConfig.getUploadPath() + "/" + subPath + "/" + fileName;
        String httpUrl = sysConfig.getResourceDomain() + "/" + subPath + "/" + fileName;

        MediaFile mediaFile = new MediaFile();
        mediaFile.setCid(cid);
        mediaFile.setMfid(mediaId);
        mediaFile.setFileName(fileName);
        mediaFile.setFileSuffix(suffix);
        mediaFile.setMediaType(mediaType);
        mediaFile.setFilePath(fileSavePath);
        mediaFile.setHttpUrl(httpUrl);
        mediaFile.setCreateTime(LocalDateTime.now());

        OutputStream outputStream = null;
        try {
            File dir = new File(sysConfig.getUploadPath() +  File.separator + subPath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            outputStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            int allBytes = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                allBytes += bytesRead;
            }
            outputStream.close();
            mediaFile.setFileSize((long)allBytes);
            save(mediaFile);
            return mediaFile;
        } catch (IOException e) {
            log.error("文件保存失败。",e);
            throw new RuntimeException(e);
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("文件流关闭失败。",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public MediaFile base64Upload(MediaFileBase64UploadRequest request) {
        String mediaId = StrUtil.uuid();
        String suffix = "png";
        String uuidFileName = mediaId + "." + suffix;
        int mediaType = getMediaTypeBySuffix(suffix);
        String mediaTypeStr = getMediaTypeStr(mediaType);
        String subPath =  mediaTypeStr + "/" + DateUtil.format(new Date(),"yyyy-MM-dd");
        String fileSavePath = sysConfig.getUploadPath() + "/" + subPath + "/" + uuidFileName;
        File file = Base64.decodeToFile(request.getBase64Image().split(",")[1], new File(fileSavePath));
        String httpUrl = sysConfig.getResourceDomain() + "/" + subPath + "/" + uuidFileName;
        long size = 0;
        try {
            InputStream inputStream =  new FileInputStream(file);
            byte[] bytes = inputStream.readAllBytes();
            size = bytes.length;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MediaFile mediaFile = new MediaFile();
        mediaFile.setCid(0);
        mediaFile.setMfid(mediaId);
        mediaFile.setFileName(uuidFileName);
        mediaFile.setFileSuffix(suffix);
        mediaFile.setMediaType(mediaType);
        mediaFile.setFilePath(fileSavePath);
        mediaFile.setFileSize(size);
        mediaFile.setHttpUrl(httpUrl);
        mediaFile.setCreateTime(LocalDateTime.now());
        save(mediaFile);
        return mediaFile;
    }

    @Override
    public List<MediaFile> listByMfids(List<String> mediaIds) {
        QueryWrapper<MediaFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("mfid",mediaIds);
        queryWrapper.orderByAsc("id");
        return list(queryWrapper);
    }

    @Override
    public void updateWithCid(List<String> mediaIds, int questionCid) {
        UpdateWrapper<MediaFile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("mfid",mediaIds);
        updateWrapper.set("cid",questionCid);
        this.update(updateWrapper);
    }

    private int getMediaTypeBySuffix(String suffix){
        //默认mediaType = 1，视频
        int mediaType = 1;
        if (StringUtils.equalsAnyIgnoreCase(suffix,"mp3","wav","acc")){
            mediaType = 2;
        }else if (StringUtils.equalsAnyIgnoreCase(suffix,"jpg","png","jpeg","gif","bmp","svg","tiff")){
            mediaType = 3;
        }
        return mediaType;
    }
    private String getMediaTypeStr(int mediaType){
        String mediaTypeStr = "";
        if (mediaType == 1){
            mediaTypeStr = "video";
        } else if (mediaType == 2){
            mediaTypeStr = "audio";
        }else if (mediaType == 3){
            mediaTypeStr = "picture";
        }
        return mediaTypeStr;
    }
}




