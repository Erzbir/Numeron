package com.erzbir.numeron;

import com.erzbir.numeron.event.AbstractEvent;
import com.erzbir.numeron.event.Event;

/**
 * @author Erzbir
 * @Data: 2024/2/22 02:32
 */
public interface NamedEvent extends Event {
    String getName();
}

class TestNamedEvent extends AbstractEvent implements NamedEvent {
    private final String name;

    public TestNamedEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
