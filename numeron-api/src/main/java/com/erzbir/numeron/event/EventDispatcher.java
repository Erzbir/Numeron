package com.erzbir.numeron.event;

/**
 * 事件调度器
 *
 * @author Erzbir
 * @Data: 2024/2/7 02:52
 */
public interface EventDispatcher extends EventProcessor, ListenerRegistrar, Cancelable {

    default void dispatch(Event event) {
        dispatch(event, GlobalEventChannel.INSTANCE);
    }

    <E extends Event> void dispatch(E event, EventChannel<E> channel);

    void addInterceptor(EventDispatchInterceptor eventDispatchInterceptor);

    void start();

    boolean isActive();

}
