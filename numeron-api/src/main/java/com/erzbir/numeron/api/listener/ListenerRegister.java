package com.erzbir.numeron.api.listener;

import net.mamoe.mirai.event.*;

import java.util.function.Consumer;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:01
 */
public interface ListenerRegister {
    <E extends Event> Listener<E> register(EventChannel<E> channel, ConcurrencyKind concurrencyKind, EventPriority eventPriority, Consumer<E> handler);
}
