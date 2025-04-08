package com.hkh.domain.response;

import com.hkh.domain.domain.MediaFile;
import lombok.Data;

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
