package com.hkh.domain.request;

import lombok.Data;

@Data
public class KnowledgeSaveRequest {

    private Integer id;

    private String kid;

    private Integer uid;

    private String kname;

    private String description;

}
