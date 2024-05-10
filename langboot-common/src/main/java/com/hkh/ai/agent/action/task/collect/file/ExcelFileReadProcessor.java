package com.hkh.ai.agent.action.task.collect.file;

/**
 * 电子表格处理器
 */
public class ExcelFileReadProcessor implements FileReadProcessor{

    @Override
    public void process(String target) {
        this.read(target);
    }

    @Override
    public void read(String filePath) {

    }
}
