package com.erzbir.numeron.event;

import com.erzbir.numeron.common.Interceptor;

/**
 * @author Erzbir
 * @Data: 2024/2/13 21:27
 */
public interface ListenerInterceptor extends Interceptor<ListenerContext> {
    @Override
    boolean intercept(ListenerContext target);
}
