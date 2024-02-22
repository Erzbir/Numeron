package com.erzbir.numeron.event;

/**
 * @author Erzbir
 */
public interface Listener<E extends Event> {
    ListenerResult onEvent(E event);
}