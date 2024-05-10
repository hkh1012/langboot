package com.hkh.ai.chain.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * 搜索引擎结果
 * @author huangkh
 */
public interface WebSearchEngine {

    InputStream search(String searchWord);

    String load(InputStream inputStream);
}
