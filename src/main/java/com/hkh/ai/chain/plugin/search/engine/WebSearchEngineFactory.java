package com.hkh.ai.chain.plugin.search.engine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebSearchEngineFactory {

    @Value("${chain.plugin.search.type}")
    private String type;

    private final BaiduWebSearchEngine baiduWebSearchEngine;
    private final BingWebSearchEngine bingWebSearchEngine;
    private final GoogleWebSearchEngine googleWebSearchEngine;

    public WebSearchEngineFactory(BaiduWebSearchEngine baiduWebSearchEngine, BingWebSearchEngine bingWebSearchEngine, GoogleWebSearchEngine googleWebSearchEngine) {
        this.baiduWebSearchEngine = baiduWebSearchEngine;
        this.bingWebSearchEngine = bingWebSearchEngine;
        this.googleWebSearchEngine = googleWebSearchEngine;
    }

    public WebSearchEngine getEngine(){
        if ("baidu".equals(type)){
            return baiduWebSearchEngine;
        }else if ("google".equals(type)){
            return googleWebSearchEngine;
        }else if ("bing".equals(type)){
            return bingWebSearchEngine;
        }
        return null;
    }
}
