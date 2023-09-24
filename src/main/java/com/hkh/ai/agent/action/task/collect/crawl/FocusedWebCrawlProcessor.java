package com.hkh.ai.agent.action.task.collect.crawl;

/**
 * 聚焦式网络爬虫
 * @author huangkh
 */
public class FocusedWebCrawlProcessor implements WebCrawlProcessor{
    @Override
    public void process(String target) {
        this.crawl(target);
    }

    @Override
    public void crawl(String target) {

    }
}
