package com.erzbir.numeron.api.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.ListenerHost;

/**
 * @author Erzbir
 * @Date: 2023/5/2 02:42
 */
public interface ListenerHostRegister {
    void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost);

    void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext);
}
