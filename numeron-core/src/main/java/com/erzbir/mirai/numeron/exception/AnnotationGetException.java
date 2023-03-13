package com.erzbir.mirai.numeron.exception;

/**
 * @author Erzbir
 * @Date: 2023/3/12 14:16
 */
public class AnnotationGetException extends FilterException {
    public AnnotationGetException(String message) {
        super(message);
    }

    protected AnnotationGetException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnnotationGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationGetException(Throwable cause) {
        super(cause);
    }
}
