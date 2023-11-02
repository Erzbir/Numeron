package com.erzbir.numeron.api.listener;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;

/**
 * @author Erzbir
 * @Date: 2023/5/2 02:42
 */
public interface ListenerHostRegister {
    default void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost) {
        registerListenerHost(channel, listenerHost, EmptyCoroutineContext.INSTANCE);
    }

    void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext);

    void registerListenerHost(EventChannel<? extends Event> channel, Object bean, CoroutineContext coroutineContext);

    default void registerListenerHost(EventChannel<? extends Event> channel, Object object) {
        registerListenerHost(channel, object, EmptyCoroutineContext.INSTANCE);
    }
}
