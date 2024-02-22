package com.erzbir.numeron.event;


import com.erzbir.numeron.common.MapAttribute;

/**
 * @author Erzbir
 * @Data: 2024/2/7 16:57
 */
public class DefaultEventContext extends MapAttribute<Object, Object> implements EventContext {
    private final Event event;

    public DefaultEventContext(Event event) {
        this.event = event;
    }

    @Override
    public Event getEvent() {
        return event;
    }

}
