package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2024/2/13 16:47
 */
public interface Disposable {
    void dispose();

    boolean isDisposed();
}
