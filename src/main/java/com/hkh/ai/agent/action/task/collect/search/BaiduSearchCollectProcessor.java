package com.hkh.ai.agent.action.task.collect.search;

/**
 * 百度搜索数据收集器
 */
public class BaiduSearchCollectProcessor implements SearchCollectProcessor{
    @Override
    public void process(String question) {
        this.search(question);
    }

    @Override
    public void search(String searchTxt) {

    }
}
