package com.erzbir.numeron.api.listener;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:01
 */
public interface EventListenerRegister {

    <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler);

    default <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Function<E, ListeningStatus> handler) {
        return subscribe(channel, eventClass, coroutineContext, concurrency, EventPriority.NORMAL, handler);
    }

    default <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Function<E, ListeningStatus> handler) {
        return subscribe(channel, eventClass, coroutineContext, ConcurrencyKind.LOCKED, handler);
    }

    default <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Function<E, ListeningStatus> handler) {
        return subscribe(channel, eventClass, EmptyCoroutineContext.INSTANCE, handler);

    }

    <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler);

    default <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler) {
        return subscribeOnce(channel, eventClass, coroutineContext, ConcurrencyKind.LOCKED, handler);
    }

    default <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler) {
        return subscribeOnce(channel, eventClass, coroutineContext, concurrency, EventPriority.NORMAL, handler);
    }

    default <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler) {
        return subscribeOnce(channel, eventClass, EmptyCoroutineContext.INSTANCE, handler);
    }

    <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler);

    default <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler) {
        return subscribeAlways(channel, eventClass, coroutineContext, ConcurrencyKind.CONCURRENT, handler);
    }

    default <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler) {
        return subscribeAlways(channel, eventClass, coroutineContext, concurrency, EventPriority.NORMAL, handler);
    }

    default <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler) {
        return subscribeAlways(channel, eventClass, EmptyCoroutineContext.INSTANCE, handler);
    }

}