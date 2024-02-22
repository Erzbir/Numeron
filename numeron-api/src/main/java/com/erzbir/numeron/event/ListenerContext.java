package com.erzbir.numeron.event;


import com.erzbir.numeron.common.AttributeContainer;

/**
 * @author Erzbir
 * @Data: 2024/2/7 02:03
 */
public interface ListenerContext extends AttributeContainer<Object, Object> {
    EventContext getEventContext();

    Listener getListener();

    default Event getEvent() {
        return getEventContext().getEvent();
    }
}
