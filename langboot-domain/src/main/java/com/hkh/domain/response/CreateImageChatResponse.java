package com.hkh.domain.response;

import com.hkh.domain.domain.MediaFile;
import lombok.Data;

import java.util.List;

@Data
public class CreateImageChatResponse {

    private String textMsg;

    private List<MediaFile> mediaFileList;
}
