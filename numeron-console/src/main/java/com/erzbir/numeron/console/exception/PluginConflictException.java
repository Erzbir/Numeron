package com.erzbir.numeron.console.exception;

/**
 * @author Erzbir
 * @Date: 2023/4/26 18:59
 */
public class PluginConflictException extends RuntimeException {
    public PluginConflictException(String message) {
        super(message);
    }

    protected PluginConflictException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PluginConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginConflictException(Throwable cause) {
        super(cause);
    }
}
