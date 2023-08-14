package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date 2023/8/14
 */
public class LoginConfigReadException extends RuntimeException {
    public LoginConfigReadException() {
        super();
    }

    public LoginConfigReadException(String message) {
        super(message);
    }

    public LoginConfigReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginConfigReadException(Throwable cause) {
        super(cause);
    }

    protected LoginConfigReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
