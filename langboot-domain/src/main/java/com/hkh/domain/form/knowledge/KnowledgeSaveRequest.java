package com.hkh.domain.form.knowledge;

import lombok.Data;

@Data
public class KnowledgeSaveRequest {

    private Integer id;

    private String kid;

    private Integer uid;

    private String kname;

    private String description;

}
