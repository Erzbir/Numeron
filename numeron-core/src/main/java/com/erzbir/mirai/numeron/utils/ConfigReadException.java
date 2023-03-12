package com.erzbir.mirai.numeron.utils;

/**
 * @author Erzbir
 * @Date: 2023/3/10 20:15
 */
public class ConfigReadException extends Exception {
    public ConfigReadException(String message) {
        super(message);
    }

    protected ConfigReadException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigReadException(Throwable cause) {
        super(cause);
    }
}
