package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2023/12/12 15:29
 */
interface InternalGlobalEventProvider {
    EventChannel<Event> getInstance();
}
