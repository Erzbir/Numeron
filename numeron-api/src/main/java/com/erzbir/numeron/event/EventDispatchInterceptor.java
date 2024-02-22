package com.erzbir.numeron.event;

import com.erzbir.numeron.common.Interceptor;

/**
 * @author Erzbir
 * @Data: 2024/2/7 18:46
 */
public interface EventDispatchInterceptor extends Interceptor<EventContext> {
    @Override
    boolean intercept(EventContext target);
}
