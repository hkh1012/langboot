package com.hkh.ai.agent.core.function;

/**
 * 工具能力类型枚举
 * @author huangkh
 */
public enum ToolFunctionType {

    SEARCH("资料搜索"),
    CRAWL("网页爬取"),
    FILE("文件读写"),
    BASH("命令脚本"),
    ANALYSIS("数据分析"),
    API_PAY("网络支付"),
    API_WEATHER("天气查询"),
    API_SMS("短信发送"),
    API_MAIL("邮件发送"),
    API_ORDER("订单查询"),
    ;

    private String type;

    ToolFunctionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
