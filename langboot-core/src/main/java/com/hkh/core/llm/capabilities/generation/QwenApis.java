package com.hkh.core.llm.capabilities.generation;

/**
 * 通义千问 AI相关 API
 */
public interface QwenApis {

    String COMPLETION_TEXT = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    /**
     * 流式文本合成
     */
    String FLOWING_TTS_URL = "wss://nls-gateway-cn-beijing.aliyuncs.com/ws/v1";

    /**
     * 短文本语音合成(300字内，超过会截断)
     */
    String SHORT_TEXT_TTS_URL = "https://nls-gateway-cn-shanghai.aliyuncs.com/stream/v1/tts";



    /**
     * 长文本语音合成
     */
    String LONG_TEXT_TTS_URL = "";

    /**
     * 一句话语音识别（60秒内）
     * 测试下来速度较慢
     */
    String ONE_SENTENCE_STT_URL = "https://nls-gateway-cn-shanghai.aliyuncs.com/stream/v1/asr";

    /**
     * 录音文件识别极速版
     */
    String FLASH_TEXT_STT_URL = "https://nls-gateway-cn-shanghai.aliyuncs.com/stream/v1/FlashRecognizer";
    /**
     * 实时语音识别（实时字幕）
     */
    String REALTIME_STT_URL = "";

    String JSON_MODEL_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
}
