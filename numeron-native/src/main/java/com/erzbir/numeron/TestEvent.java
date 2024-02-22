package com.erzbir.numeron;

import com.erzbir.numeron.event.AbstractEvent;
import com.erzbir.numeron.event.Event;

/**
 * @author Erzbir
 * @Data: 2024/2/14 02:19
 */
public class TestEvent extends AbstractEvent implements Event {
    public TestEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return source;
    }

    public String getStr() {
        return "test";
    }
}
