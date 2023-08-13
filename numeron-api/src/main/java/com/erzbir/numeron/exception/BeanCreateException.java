package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date 2023/7/26
 */
public class BeanCreateException extends RuntimeException {
    public BeanCreateException() {
        super();
    }

    public BeanCreateException(String message) {
        super(message);
    }

    public BeanCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreateException(Throwable cause) {
        super(cause);
    }

    protected BeanCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
