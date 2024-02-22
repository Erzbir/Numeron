package com.erzbir.numeron.common;

/**
 * 拦截器
 * <p></p>
 * 返回 {@code true} 则代表拦截
 *
 * @author Erzbir
 * @Data: 2024/2/3 11:37
 */
public interface Interceptor<E> {

    boolean intercept(E target);
}
