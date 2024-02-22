package com.erzbir.numeron.event;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Erzbir
 * @Data: 2024/2/22 00:08
 */
@Slf4j
public abstract class AbstractEventDispatcher implements EventDispatcher {
    protected final List<EventDispatchInterceptor> eventDispatchInterceptors = new ArrayList<>();
    protected final AtomicBoolean activated = new AtomicBoolean(false);

    @Override
    public <E extends Event> void dispatch(E event, EventChannel<E> channel) {
        log.debug("Received event: {}", event);
        DefaultEventContext eventContext = new DefaultEventContext(event);
        if (!intercept(eventContext)) {
            return;
        }
        if (channel.isCanceled()) {
            log.debug("EventChannel: {} is already shutdown, dispatching canceled", channel.getClass().getSimpleName());
            return;
        }
        if (!channel.getListeners().iterator().hasNext()) {
            log.debug("EventChannel: {} has no listener, dispatching canceled", channel.getClass().getSimpleName());
        }
        dispatchTo(event, channel);
    }

    protected abstract <E extends Event> void dispatchTo(E event, EventChannel<E> channel);

    private boolean intercept(EventContext eventContext) {
        for (EventDispatchInterceptor eventDispatchInterceptor : eventDispatchInterceptors) {
            if (!eventDispatchInterceptor.intercept(eventContext)) {
                Event event = eventContext.getEvent();
                event.intercepted();
                log.debug("Event : {} was truncated", event);
                return false;
            }
        }
        return true;
    }

    @Override
    public void addInterceptor(EventDispatchInterceptor eventDispatchInterceptor) {
        eventDispatchInterceptors.add(eventDispatchInterceptor);
    }

    @Override
    public void start() {
        activated.set(true);
    }

    @Override
    public boolean isActive() {
        return activated.get();
    }

    @Override
    public void addInterceptor(ListenerInterceptor listenerInterceptor, EventChannel<Event> channel) {
        channel.addInterceptor(listenerInterceptor);
    }

    @Override
    public <T extends Event> ListenerHandle register(EventChannel<T> channel, Class<T> eventType, Listener<T> listener) {
        return channel.registerListener(eventType, listener);
    }

    @Override
    public boolean isCanceled() {
        return !activated.get();
    }

    @Override
    public void cancel() {
        activated.set(false);
    }
}
