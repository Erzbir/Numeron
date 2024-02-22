package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2023/12/6 11:16
 */
public class StopEvent extends AbstractEvent {
    public StopEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return source;
    }

}
