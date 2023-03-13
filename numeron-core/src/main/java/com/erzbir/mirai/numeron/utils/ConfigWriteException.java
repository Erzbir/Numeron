package com.erzbir.mirai.numeron.utils;

/**
 * @author Erzbir
 * @Date: 2023/3/10 20:17
 */
public class ConfigWriteException extends Exception {
    public ConfigWriteException(String message) {
        super(message);
    }

    protected ConfigWriteException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigWriteException(Throwable cause) {
        super(cause);
    }
}
