package com.erzbir.mirai.numeron.exception;

/**
 * @author Erzbir
 * @Date: 2023/3/12 14:18
 */
public class FilterException extends RuntimeException{
    public FilterException(String message) {
        super(message);
    }

    protected FilterException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(Throwable cause) {
        super(cause);
    }
}
