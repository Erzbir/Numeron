package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.api.listener.EventListenerRegister;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/29 10:37
 */
public class PluginEventListenerRegisterImpl implements EventListenerRegister {
    private EventListenerRegister Global;
    private EventListenerRegister Plugin;
    private EventListenerRegister Bot;

    @Override
    public <E extends Event> net.mamoe.mirai.event.Listener<E> register(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
        return null;
    }

    @Override
    public <E extends Event> net.mamoe.mirai.event.Listener<E> registerOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return null;
    }

    @Override
    public <E extends Event> Listener<E> registerAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return null;
    }
}
