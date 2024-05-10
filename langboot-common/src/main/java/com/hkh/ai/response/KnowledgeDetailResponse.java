package com.hkh.ai.response;

import com.hkh.ai.domain.KnowledgeAttach;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KnowledgeDetailResponse {

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
     *
     */
    private List<KnowledgeAttach> attachList = new ArrayList<>();
}
