package com.hkh.core.vectorizer;

import com.hkh.core.vectorizer.baidu.BaiduQianFanVectorization;
import com.hkh.core.vectorizer.huggingface.HuggingFaceInferenceVectorization;
import com.hkh.core.vectorizer.local.LocalAiVectorization;
import com.hkh.core.vectorizer.openai.OpenAiVectorization;
import com.hkh.core.vectorizer.zhipu.ZhipuAiVectorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 向量化工厂：提供各种向量化模型的能力
 * @author huangkh
 */
@Component
@Slf4j
public class VectorizationFactory {

    @Value("${chain.vectorization.type}")
    private String type;
    private final OpenAiVectorization openAiVectorization;
    private final LocalAiVectorization localAiVectorization;
    private final HuggingFaceInferenceVectorization huggingFaceInferenceVectorization;
    private final BaiduQianFanVectorization baiduQianFanVectorization;
    private final ZhipuAiVectorization zhipuAiVectorization;

    public VectorizationFactory(OpenAiVectorization openAiVectorization,
                                LocalAiVectorization localAiVectorization,
                                HuggingFaceInferenceVectorization huggingFaceInferenceVectorization,
                                BaiduQianFanVectorization baiduQianFanVectorization, ZhipuAiVectorization zhipuAiVectorization) {
        this.openAiVectorization = openAiVectorization;
        this.localAiVectorization = localAiVectorization;
        this.huggingFaceInferenceVectorization = huggingFaceInferenceVectorization;
        this.baiduQianFanVectorization = baiduQianFanVectorization;
        this.zhipuAiVectorization = zhipuAiVectorization;
    }

    public Vectorization getEmbedding(){
        if ("openai".equals(type)){
            return openAiVectorization;
        }else if ("local".equals(type)){
            return localAiVectorization;
        }else if ("huggingFace".equals(type)){
            return huggingFaceInferenceVectorization;
        }else if ("baidu".equals(type)){
            return baiduQianFanVectorization;
        }else if ("zhipu".equals(type)) {
            return zhipuAiVectorization;
        }else {
            return null;
        }

    }
}
