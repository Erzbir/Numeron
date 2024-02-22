package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2024/2/13 16:57
 */
public interface Cancelable {
    void cancel();

    boolean isCanceled();
}
