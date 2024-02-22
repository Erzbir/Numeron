package com.erzbir.numeron.event;


/**
 * @author Erzbir
 * @Data: 2023/12/12 15:00
 */
public class GlobalEventChannelProviderImpl implements InternalGlobalEventProvider {
    @Override
    public EventChannel<Event> getInstance() {
        return EventChannelDispatcher.INSTANCE;
    }
}
