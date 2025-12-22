package com.hkh.domain.vo.chat;

import com.hkh.domain.domain.MediaFile;
import lombok.Data;

import java.util.List;

@Data
public class CreateImageChatResponse {

    private String textMsg;

    private List<MediaFile> mediaFileList;
}
