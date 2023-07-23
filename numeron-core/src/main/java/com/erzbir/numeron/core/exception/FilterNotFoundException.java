package com.erzbir.numeron.core.exception;

/**
 * @author Erzbir
 * @Data 2023/7/23
 */
public class FilterNotFoundException extends RuntimeException {
    public FilterNotFoundException() {
        super();
    }

    public FilterNotFoundException(String message) {
        super(message);
    }

    public FilterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FilterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
