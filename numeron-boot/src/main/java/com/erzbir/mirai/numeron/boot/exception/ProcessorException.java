package com.erzbir.mirai.numeron.boot.exception;

/**
 * @author Erzbir
 * @Date: 2023/3/11 21:58
 */
public class ProcessorException extends RuntimeException {
    public ProcessorException(String message) {
        super(message);
    }

    public ProcessorException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessorException(Throwable cause) {
        super(cause);
    }
}
