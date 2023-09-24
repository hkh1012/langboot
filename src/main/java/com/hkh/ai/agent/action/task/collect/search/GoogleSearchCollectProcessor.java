package com.hkh.ai.agent.action.task.collect.search;

/**
 * 谷歌搜索数据收集器
 */
public class GoogleSearchCollectProcessor implements SearchCollectProcessor{
    @Override
    public void process(String question) {
        this.search(question);
    }

    @Override
    public void search(String searchTxt) {

    }
}
