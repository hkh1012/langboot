package com.hkh.agent.action.task.collect.crawl;


import com.hkh.agent.action.task.TaskProcessor;

/**
 * 爬虫处理器
 * @author huangkh
 */
public interface WebCrawlProcessor extends TaskProcessor {

    void crawl(String target);

}
