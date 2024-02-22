package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2023/12/6 11:15
 */
public class StartupEvent extends AbstractEvent {
    public StartupEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return source;
    }
}
