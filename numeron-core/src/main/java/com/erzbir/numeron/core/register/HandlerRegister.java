package com.erzbir.numeron.core.register;

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
    void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrencyKind);
}
