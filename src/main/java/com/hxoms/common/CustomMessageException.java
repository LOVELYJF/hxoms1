package com.hxoms.common;

/**
 * 统一异常提示
 *
 * @author sunqian
 * @date 2020/4/15 16:11
 */
public class CustomMessageException extends RuntimeException {
    public CustomMessageException(String message) {
        super(message);
    }

    public CustomMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomMessageException(Throwable cause) {
        super(cause);
    }

    protected CustomMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
