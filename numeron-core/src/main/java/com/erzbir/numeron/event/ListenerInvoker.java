package com.erzbir.numeron.event;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Erzbir
 * @Data: 2024/2/7 02:46
 */
public sealed interface ListenerInvoker {
    ListenerResult invoke(ListenerContext listenerContext);
}

@Slf4j
final class BaseListenerInvoker implements ListenerInvoker {

    @SuppressWarnings("unchecked")
    @Override
    public ListenerResult invoke(ListenerContext listenerContext) {
        Event event = listenerContext.getEvent();
        return listenerContext.getListener().onEvent(event);
    }
}

@Slf4j
final class InterceptorInvoker implements ListenerInvoker {
    public ListenerInvoker listenerInvoker;
    public List<ListenerInterceptor> listenerInterceptors;

    public InterceptorInvoker(List<ListenerInterceptor> listenerInterceptors) {
        this.listenerInvoker = new BaseListenerInvoker();
        this.listenerInterceptors = listenerInterceptors;
    }

    @Override
    public ListenerResult invoke(ListenerContext listenerContext) {
        if (!intercept(listenerContext)) {
            return StandardListenerResult.TRUNCATED;
        }
        return listenerInvoker.invoke(listenerContext);
    }


    private boolean intercept(ListenerContext listenerContext) {
        for (ListenerInterceptor listenerInterceptor : listenerInterceptors) {
            if (!listenerInterceptor.intercept(listenerContext)) {
                Listener<?> listener = listenerContext.getListener();
                log.debug("Listener: {} was truncated", listener);
                return false;
            }
        }
        return true;
    }

}