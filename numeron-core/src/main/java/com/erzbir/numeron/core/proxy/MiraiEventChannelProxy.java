package com.erzbir.numeron.core.proxy;

import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/22 00:51
 */
public class MiraiEventChannelProxy {
    private final EventChannelMethodInvokeInter proxy;
    private Runnable functionBefore;
    private Runnable functionAfter;
    private Map<String, Object> argsMap;
    private Object ret;

    public MiraiEventChannelProxy(@NotNull Map<String, Object> argsMap) {
        this(() -> {
        }, () -> {
        }, argsMap);
    }

    public MiraiEventChannelProxy(Runnable functionBefore, Runnable functionAfter, @NotNull Map<String, Object> argsMap) {
        EventChannelMethodInvokeInter proxyMethodMap = new EventChannelMethodInvokeImpl();
        Class<? extends EventChannelMethodInvokeInter> aClass = proxyMethodMap.getClass();
        proxy = (EventChannelMethodInvokeInter) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationImpl(proxyMethodMap));
        this.functionAfter = functionAfter;
        this.functionBefore = functionBefore;
        this.argsMap = argsMap;
    }

    public Object getArgv(String name) {
        return argsMap.get(name);
    }

    public void setArgsMap(Map<String, Object> argsMap) {
        this.argsMap = argsMap;
    }

    public Object getRet() {
        return ret;
    }

    public EventChannelMethodInvokeInter getProxy() {
        return proxy;
    }

    public void setInvokeBefore(Runnable function) {
        functionBefore = function;
    }

    public void setInvokeAfter(Runnable function) {
        functionAfter = function;
    }


    @SuppressWarnings({"unused"})
    public interface EventChannelMethodInvokeInter {
        <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler);

        <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Function<E, ListeningStatus> handler);

        <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Function<E, ListeningStatus> handler);

        <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Function<E, ListeningStatus> handler);

        <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler);

        <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler);

    }

    @SuppressWarnings("unchecked")
    private record EventChannelMethodInvokeImpl() implements EventChannelMethodInvokeInter {
        private static final ExecutorService executor = Executors.newCachedThreadPool();


        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
            try {
                return (Listener<E>) executor.submit(() -> {
                    channel.subscribe(eventClass, coroutineContext, concurrency, priority, handler);
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                NumeronLogUtil.logger.error(e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Function<E, ListeningStatus> handler) {
            return subscribe(channel, eventClass, coroutineContext, concurrency, EventPriority.NORMAL, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Function<E, ListeningStatus> handler) {
            return subscribe(channel, eventClass, coroutineContext, ConcurrencyKind.LOCKED, EventPriority.NORMAL, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Function<E, ListeningStatus> handler) {
            return channel.subscribe(eventClass, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
            return channel.subscribeAlways(eventClass, coroutineContext, concurrency, priority, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler) {
            return channel.subscribeOnce(eventClass, coroutineContext, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler) {
            return channel.subscribeOnce(eventClass, coroutineContext, concurrency, handler);

        }

        @Override
        public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler) {
            return channel.subscribeOnce(eventClass, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
            return channel.subscribeAlways(eventClass, coroutineContext, concurrency, priority, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Consumer<E> handler) {
            return channel.subscribeAlways(eventClass, coroutineContext, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Consumer<E> handler) {
            return channel.subscribeAlways(eventClass, coroutineContext, concurrency, handler);

        }

        @Override
        public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, Consumer<E> handler) {
            return channel.subscribeAlways(eventClass, handler);
        }
    }

    private class InvocationImpl implements InvocationHandler {
        private final Object target;

        public InvocationImpl(Object target) {
            this.target = target;
        }

        public Object getTarget() {
            return target;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            functionBefore.run();
            ret = method.invoke(target, args);
            functionAfter.run();
            return ret;
        }
    }
}
