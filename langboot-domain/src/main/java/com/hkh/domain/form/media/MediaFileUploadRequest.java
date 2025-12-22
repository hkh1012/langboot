package com.hkh.domain.form.media;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MediaFileUploadRequest {

    private MultipartFile file;

}
