package com.hkh.core.llm.capabilities.generation.audio;

import java.io.File;
import java.io.InputStream;

/**
 * 语音聊天服务
 * @author huangkh
 */
public interface AudioChatService {
    String audioToText(File audio, String prompt);

    InputStream createSpeech(String content,String voiceType);
}
