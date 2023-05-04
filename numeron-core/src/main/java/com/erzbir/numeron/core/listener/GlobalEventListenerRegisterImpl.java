package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.api.listener.EventListenerRegister;
import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.core.proxy.MiraiEventChannelProxy;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/29 10:09
 */
public class GlobalEventListenerRegisterImpl implements EventListenerRegister {

    MiraiEventChannelProxy miraiEventChannelProxy = new MiraiEventChannelProxy(new HashMap<>());

    public <E extends Event> net.mamoe.mirai.event.Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
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
    public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
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
    public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
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
