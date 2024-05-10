package com.hkh.ai.chain.engine;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class WebSearchEngineWrapper implements WebSearchEngine{

    private final WebSearchEngineFactory webSearchEngineFactory;
    @Override
    public InputStream search(String searchWord) {
        WebSearchEngine engine = webSearchEngineFactory.getEngine();
        return engine.search(searchWord);
    }

    @Override
    public String load(InputStream inputStream) {
        WebSearchEngine engine = webSearchEngineFactory.getEngine();
        return engine.load(inputStream);
    }
}
