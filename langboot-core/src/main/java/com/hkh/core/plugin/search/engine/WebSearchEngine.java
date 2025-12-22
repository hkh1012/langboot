package com.hkh.core.plugin.search.engine;

import java.io.InputStream;

/**
 * 搜索引擎结果
 * @author huangkh
 */
public interface WebSearchEngine {

    InputStream search(String searchWord);

    String load(InputStream inputStream);
}
