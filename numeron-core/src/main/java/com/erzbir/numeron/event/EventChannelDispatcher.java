package com.erzbir.numeron.event;


/**
 * 事件的广播, 订阅等都委托到此处处理
 *
 * @author Erzbir
 * @Data: 2023/12/12 08:56
 */
public class EventChannelDispatcher<E extends Event> extends EventChannelImpl<E> {
    public static final EventChannelDispatcher<Event> INSTANCE = new EventChannelDispatcher<>(Event.class);

    private EventChannelDispatcher(Class<E> baseEventClass) {
        super(baseEventClass);
    }

    @Override
    public void broadcast(EventContext eventContext) {
        super.broadcast(eventContext);
    }
}
