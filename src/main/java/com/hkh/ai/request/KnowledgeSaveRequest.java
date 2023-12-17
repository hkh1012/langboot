package com.hkh.ai.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class KnowledgeSaveRequest {

    private Integer id;

    private String kid;

    private Integer uid;

    private String kname;

    private String description;

}
