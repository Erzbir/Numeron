package com.erzbir.numeron.event;


import com.erzbir.numeron.common.Context;

/**
 * @author Erzbir
 * @Data: 2024/2/7 02:03
 */
public interface ListenerContext extends Context {
    EventContext getEventContext();

    Listener getListener();

    default Event getEvent() {
        return getEventContext().getEvent();
    }
}
