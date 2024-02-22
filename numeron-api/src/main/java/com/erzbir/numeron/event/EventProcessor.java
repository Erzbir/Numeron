package com.erzbir.numeron.event;

/**
 * 事件流程处理器
 *
 * @author Erzbir
 * @Data: 2024/2/7 02:52
 */
public interface EventProcessor {
    void addInterceptor(ListenerInterceptor listenerInterceptor, EventChannel<Event> channel);
}
