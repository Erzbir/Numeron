package com.erzbir.mirai.numeron.plugins.rss.exception;

/**
 * @author Erzbir
 * @Date: 2023/3/10 19:26
 */
public class ImageGetException extends Exception {
    public ImageGetException(String message) {
        super(message);
    }

    public ImageGetException(String message, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ImageGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageGetException(Throwable cause) {
        super(cause);
    }
}
