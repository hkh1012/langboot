package com.hkh.ai.chain.llm.capabilities.generation;

/**
 * 智普AI相关 API
 */
public interface ZhipuChatApis {

    String COMPLETION_TEXT = "https://open.bigmodel.cn/api/paas/v4/chat/completions";

    String COMPLETION_IMAGE = "https://open.bigmodel.cn/api/paas/v4/images/generations";

    String COMPLETION_CHARACTER = "https://open.bigmodel.cn/api/paas/v3/model-api/charglm-3/sse-invoke";

    String COMPLETION_VISION = "https://open.bigmodel.cn/api/paas/v4/chat/completions";

    String EMBEDDING_EMBEDDING_V2 = "https://open.bigmodel.cn/api/paas/v4/embeddings";

    String FILE_UPLOAD = "https://open.bigmodel.cn/api/paas/v4/files";

    String FILE_LIST = "https://open.bigmodel.cn/api/paas/v4/files";

    String FINE_TURNING_CREATE_JOB = "https://open.bigmodel.cn/api/paas/v4/fine_tuning/jobs";

    String FINE_TURNING_JOB_EVENTS = "https://open.bigmodel.cn/api/paas/v4/fine_tuning/jobs/<job_id>/events?limit=50";

    String FINE_TURNING_JOB = "https://open.bigmodel.cn/api/paas/v4/fine_tuning/jobs/<job_id>";

    String FINE_TURNING_JOBS = "https://open.bigmodel.cn/api/paas/v4/fine_tuning/jobs";

}
