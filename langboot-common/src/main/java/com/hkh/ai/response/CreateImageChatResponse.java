package com.hkh.ai.response;

import com.hkh.ai.domain.MediaFile;
import lombok.Data;

import java.util.List;

@Data
public class CreateImageChatResponse {

    private String textMsg;

    private List<MediaFile> mediaFileList;
}
