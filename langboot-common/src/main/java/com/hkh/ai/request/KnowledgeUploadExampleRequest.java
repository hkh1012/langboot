package com.hkh.ai.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class KnowledgeUploadExampleRequest {

    private String kid;

    private MultipartFile file;

}
