package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date: 2023/4/26 18:43
 */
public class PluginLoadException extends RuntimeException {
    public PluginLoadException(String message) {
        super(message);
    }

    protected PluginLoadException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PluginLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginLoadException(Throwable cause) {
        super(cause);
    }
}
