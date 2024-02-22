package com.erzbir.numeron.event;


import com.erzbir.numeron.common.MapAttribute;

/**
 * @author Erzbir
 * @Data: 2024/2/7 18:04
 */
@SuppressWarnings("rawtypes")
public class DefaultListenerContext extends MapAttribute<Object, Object> implements ListenerContext {
    private final EventContext eventContext;
    private final Listener listener;

    public DefaultListenerContext(EventContext eventContext, Listener listener) {
        this.eventContext = eventContext;
        this.listener = listener;
    }

    @Override
    public EventContext getEventContext() {
        return eventContext;
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
