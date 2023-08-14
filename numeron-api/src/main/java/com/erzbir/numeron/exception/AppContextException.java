package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date: 2023/3/12 14:12
 */
public class AppContextException extends RuntimeException {
    public AppContextException(String message) {
        super(message);
    }

    protected AppContextException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppContextException(Throwable cause) {
        super(cause);
    }
}
