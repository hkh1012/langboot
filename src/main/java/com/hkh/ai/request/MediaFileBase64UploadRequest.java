package com.hkh.ai.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MediaFileBase64UploadRequest {

    private String base64Image;

}
