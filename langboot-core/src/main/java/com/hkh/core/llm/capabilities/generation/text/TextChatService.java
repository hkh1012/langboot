package com.hkh.core.llm.capabilities.generation.text;

import com.hkh.domain.dto.FluxStreamDto;
import com.hkh.domain.dto.HistoryMessageDto;

import java.util.List;

/**
 * 文本聊天服务接口
 * @author huangkh
 */
public interface TextChatService {
    /**
     * 阻塞推理
     * 包括所有上下文信息，如知识库内容、历史消息等，全部传入
     * @param content
     * @return
     */
    String blockCompletion(String content);

    /**
     * 流式聊天_新版
     * 返回一个队列，将所有消息推送到队列中,应用端自行处理队列中的消息数据
     * 将消息发送方式、数据保存等逻辑从接口实现逻辑中剔除，接口的实现仅为大语言模型的调用逻辑
     * @param nearestList 知识库中相似文本列表
     * @param historyList 历史消息列表(不包含当前消息)
     *
     */
    FluxStreamDto stream(String content, String systemPrompt, List<String> nearestList, List<HistoryMessageDto> historyList);
}
