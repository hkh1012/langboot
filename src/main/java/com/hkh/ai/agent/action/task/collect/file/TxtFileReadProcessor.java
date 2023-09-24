package com.hkh.ai.agent.action.task.collect.file;

/**
 * 文本文件处理器
 */
public class TxtFileReadProcessor implements FileReadProcessor{

    @Override
    public void process(String target) {
        this.read(target);
    }

    @Override
    public void read(String filePath) {

    }
}
