package com.hkh.common.service;

import com.hkh.domain.domain.MediaFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkh.domain.request.MediaFileBase64UploadRequest;
import com.hkh.domain.request.MediaFileUploadRequest;

import java.io.InputStream;
import java.util.List;

/**
* @author huangkh
* @description 针对表【media_file(媒体文件)】的数据库操作Service
* @createDate 2024-01-11 13:57:30
*/
public interface MediaFileService extends IService<MediaFile> {

    MediaFile upload(MediaFileUploadRequest request);

    MediaFile getByMfid(String mediaId);

    MediaFile saveFile(InputStream speech, int cid);

    MediaFile base64Upload(MediaFileBase64UploadRequest request);

    List<MediaFile> listByMfids(List<String> mediaIds);

    void updateWithCid(List<String> mediaIds, int questionCid);
}
