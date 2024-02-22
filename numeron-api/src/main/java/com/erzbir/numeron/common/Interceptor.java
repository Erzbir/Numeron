package com.erzbir.numeron.common;

/**
 * @author Erzbir
 * @Data: 2024/2/3 11:37
 */
public interface Interceptor<E> {

    boolean intercept(E target);
}
