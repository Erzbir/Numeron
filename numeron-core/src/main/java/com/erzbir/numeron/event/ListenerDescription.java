package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2024/2/13 18:59
 */
@SuppressWarnings("rawtypes")
public record ListenerDescription(Class<Event> eventType, Listener listener) {
}