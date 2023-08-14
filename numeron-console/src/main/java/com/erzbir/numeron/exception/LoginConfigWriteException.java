package com.erzbir.numeron.exception;

/**
 * @author Erzbir
 * @Date 2023/8/14
 */
public class LoginConfigWriteException extends RuntimeException {
    public LoginConfigWriteException() {
        super();
    }

    public LoginConfigWriteException(String message) {
        super(message);
    }

    public LoginConfigWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginConfigWriteException(Throwable cause) {
        super(cause);
    }

    protected LoginConfigWriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
