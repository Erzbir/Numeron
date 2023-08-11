package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.api.listener.ListenerRegisterInter;
import com.erzbir.numeron.core.context.ListenerContext;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/6/9 16:04
 * <p>
 *     默认的消息注册器
 * </p>
 */
public class DefaultListenerRegisterImpl implements ListenerRegisterInter {
    @Override
    public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
        return ListenerContext.INSTANCE.getListenerRegister().subscribe(
                channel,
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
                channel,
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
                channel,
                eventClass,
                coroutineContext,
                concurrency,
                priority,
                handler
        );
    }

    @Override
    public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost) {
        ListenerContext.INSTANCE.getListenerRegister().registerListenerHost(channel, listenerHost);
    }

    @Override
    public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext) {
        ListenerContext.INSTANCE.getListenerRegister().registerListenerHost(channel, listenerHost, coroutineContext);
    }

    @Override
    public void setRunBefore(Runnable before) {
        ListenerContext.INSTANCE.getListenerRegister().setRunBefore(before);
    }

    @Override
    public void setRunAfter(Runnable after) {
        ListenerContext.INSTANCE.getListenerRegister().setRunAfter(after);
    }
}
