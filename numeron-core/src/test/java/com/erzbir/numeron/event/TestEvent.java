package com.erzbir.numeron.event;

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

    public String getName() {
        return "test";
    }
}
