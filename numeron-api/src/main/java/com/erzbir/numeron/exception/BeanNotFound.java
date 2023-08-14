package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date 2023/7/25
 */
public class BeanNotFound extends RuntimeException {
    public BeanNotFound() {
        super();
    }

    public BeanNotFound(String message) {
        super(message);
    }

    public BeanNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanNotFound(Throwable cause) {
        super(cause);
    }

    protected BeanNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
