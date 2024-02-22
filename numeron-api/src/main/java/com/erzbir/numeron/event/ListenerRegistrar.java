package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2024/2/8 00:46
 */
public interface ListenerRegistrar {
    <T extends Event> ListenerHandle register(EventChannel<T> channel, Class<T> eventType, Listener<T> listener);
}
