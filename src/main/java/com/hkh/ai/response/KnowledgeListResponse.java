package com.hkh.ai.response;

import com.hkh.ai.domain.KnowledgeAttach;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KnowledgeListResponse {

    private Integer id;

    /**
     * 知识库ID
     */
    private String kid;

    /**
     * 知识库名称
     */
    private String kname;


    /**
     * 角色
     */
    private String role;

    /**
     * 附件列表
     */
    private List<KnowledgeAttach> attachList = new ArrayList<>();
}
