package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.api.listener.EventListenerRegister;
import com.erzbir.numeron.core.context.ListenerContext;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/29 10:09
 */
public class GlobalEventListenerRegisterImpl implements EventListenerRegister {
    private EventListenerRegister Global;
    private EventListenerRegister Plugin;
    private EventListenerRegister Bot;

    public <E extends Event> net.mamoe.mirai.event.Listener<E> register(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
        return ListenerContext.INSTANCE.getListenerRegister().subscribe(
                GlobalEventChannel.INSTANCE,
                eventClass,
                coroutineContext,
                concurrency,
                priority,
                handler
        );
    }

    @Override
    public <E extends Event> net.mamoe.mirai.event.Listener<E> registerOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return ListenerContext.INSTANCE.getListenerRegister().subscribeOnce(
                GlobalEventChannel.INSTANCE,
                eventClass,
                coroutineContext,
                concurrency,
                priority,
                handler
        );
    }

    @Override
    public <E extends Event> Listener<E> registerAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return ListenerContext.INSTANCE.getListenerRegister().subscribeAlways(
                GlobalEventChannel.INSTANCE,
                eventClass,
                coroutineContext,
                concurrency,
                priority,
                handler
        );
    }
}
