package com.erzbir.numeron.api.exception;

/**
 * @author Erzbir
 * @Date 2023/7/26
 */
public class BeanReflectException extends RuntimeException {
    public BeanReflectException() {
        super();
    }

    public BeanReflectException(String message) {
        super(message);
    }

    public BeanReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanReflectException(Throwable cause) {
        super(cause);
    }

    protected BeanReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
