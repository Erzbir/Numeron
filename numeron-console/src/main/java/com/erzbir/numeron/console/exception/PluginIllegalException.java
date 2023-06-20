package com.erzbir.numeron.console.exception;

/**
 * @author Erzbir
 * @Date: 2023/6/19 05:07
 */
public class PluginIllegalException extends RuntimeException {
    public PluginIllegalException() {
        super();
    }

    public PluginIllegalException(String message) {
        super(message);
    }

    public PluginIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginIllegalException(Throwable cause) {
        super(cause);
    }

    protected PluginIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
