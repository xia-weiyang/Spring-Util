package com.jiushig.springutil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zk on 2018/6/13.
 * 日志信息构建
 */
public final class LoggerBuilder {
    private String message;
    private String describe;
    private Integer userId;
    private Map<String, Object> otherMessage;

    private LoggerBuilder(String message) {
        assert message != null;
        this.message = message;
    }

    public LoggerBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public static LoggerBuilder get(String message) {
        return new WeakReference<>(new LoggerBuilder(message)).get();
    }

    public LoggerBuilder setDescribe(Object describe) {
        this.describe = describe == null ? "null" : describe.toString();
        return this;
    }

    public LoggerBuilder set(String key, Object object) {
        assert key != null;
        if (otherMessage == null) {
            otherMessage = new HashMap<>();
        }
        otherMessage.put(key, object);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String build() {
        return this.toString();
    }

    @Override
    public String toString() {
        return message
                + (describe == null ? "" : "  " + describe)
                + (userId == null ? "" : "  [" + userId + "]")
                + (otherMessage == null ? "" : "  " + otherMessage);
    }
}
