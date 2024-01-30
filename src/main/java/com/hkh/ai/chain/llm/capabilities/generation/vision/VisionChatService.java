package com.hkh.ai.chain.llm.capabilities.generation.vision;

import java.util.List;

/**
 * 图文多模态接口(即图像理解)
 * @author huangkh
 */
public interface VisionChatService {


    /**
     * 图像理解
     * @param content
     * @param imageUrlList
     * @return
     */
    String visionCompletion(String content, List<String> imageUrlList);
}
