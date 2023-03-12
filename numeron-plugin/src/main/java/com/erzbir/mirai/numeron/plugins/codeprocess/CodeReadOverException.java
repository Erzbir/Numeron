package com.erzbir.mirai.numeron.plugins.codeprocess;

/**
 * @author Erzbir
 * @Date: 2023/3/12 14:04
 */
public class CodeReadOverException extends Exception {
    public CodeReadOverException(String message) {
        super(message);
    }

    protected CodeReadOverException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CodeReadOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeReadOverException(Throwable cause) {
        super(cause);
    }
}
