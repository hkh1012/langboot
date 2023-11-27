package com.hkh.ai.domain.function;

/**
 * 天气温度单位
 */
public enum DatePeriod {
    THE_DAY_BEFORE_YESTERDAY("前天"),
    YESTERDAY("昨天"),
    TODAY("昨天"),
    TOMORROW("明天"),
    THE_DAY_AFTER_TOMORROW("后天"),
    WITHIN_THE_NEXT_WEEK("未来一周");

    private String message;

    DatePeriod(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getValueByName(String name) {
        for (DatePeriod datePeriod : DatePeriod.values()) {
            if (datePeriod.name().equals(name)) {
                return datePeriod.getMessage();
            }
        }
        return null;
    }
}
