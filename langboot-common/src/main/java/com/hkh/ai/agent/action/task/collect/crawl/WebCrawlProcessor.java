package com.hkh.ai.agent.action.task.collect.crawl;

import com.hkh.ai.agent.action.task.TaskProcessor;

/**
 * 爬虫处理器
 * @author huangkh
 */
public interface WebCrawlProcessor extends TaskProcessor {

    void crawl(String target);

}
