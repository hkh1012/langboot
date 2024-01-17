package com.hkh.ai.response;

import com.hkh.ai.domain.KnowledgeAttach;
import com.hkh.ai.domain.MediaFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AudioChatResponse {


    /**
     * 文本内容
     */
    private String content;

    /**
     * 媒体文件
     */
    private MediaFile mediaFile;

    /**
     * 相似片段列表
     */
    private List<String> nearestList;

}
