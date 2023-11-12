package com.hkh.ai.chain.vectorizer;

import lombok.Data;

import java.util.List;

@Data
public class BaiduQianFanVectorizationResult {

    private String id;
    private String object;
    private Long created;
    private List<BaiuQianFanVectorizationResultItem> data;
    private Usage usage;

    @Data
    class BaiuQianFanVectorizationResultItem{
        private String object;
        private List<Double> embedding;
        private int index;
    }

    @Data
    class Usage{
        private int prompt_tokens;
        private int total_tokens;
    }
//
//
//    {
//        "id": "as-gjs275mj6s",
//            "object": "embedding_list",
//            "created": 1687155816,
//            "data": [
//        {
//            "object": "embedding",
//                "embedding": [
//            0.018314670771360397,
//                    0.00942440889775753,
//        ...（1024 float64 in total）
//            -0.36294862627983093
//      ],
//            "index": 0
//        },
//        {
//            "object": "embedding",
//                "embedding": [
//            0.12250778824090958,
//                    0.07934671640396118,
//        ...（1024 float64 in total）
//            0
//      ],
//            "index": 1
//        }
//  ],
//        "usage": {
//        "prompt_tokens": 12,
//                "total_tokens": 12
//    }
//    }

}
