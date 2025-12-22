package com.hkh.domain.form.knowledge;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class KnowledgeUploadRequest {

    private String kid;

    private MultipartFile file;

}
