package com.erzbir.numeron.core.register;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventPriority;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public interface HandlerRegister {
    default void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrency) {
        register(bean, method, channel, eventPriority, concurrency, EmptyCoroutineContext.INSTANCE);
    }

    void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrency, CoroutineContext coroutineContext);
}
