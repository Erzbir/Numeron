package com.erzbir.numeron.core.proxy;

import com.erzbir.numeron.core.entity.NumeronBot;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2023/4/22 00:51
 */
public class MiraiEventChannel$Proxy {
    private final EventChannelMethodInvokeInter proxy;
    private Runnable functionBefore;
    private Runnable functionAfter;

    private Map<String, Object> argsMap;
    private Object ret;


    public MiraiEventChannel$Proxy(@NotNull Map<String, Object> argsMap) {
        this(
                () -> {
                },
                () -> {
                },
                argsMap
        );
    }

    public MiraiEventChannel$Proxy(Runnable functionBefore, Runnable functionAfter, @NotNull Map<String, Object> argsMap) {
        EventChannelMethodInvokeInter proxyMethodMap = new EventChannelMethodInvokeImpl();
        Class<? extends EventChannelMethodInvokeInter> aClass = proxyMethodMap.getClass();
        proxy = (EventChannelMethodInvokeInter) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationImpl(proxyMethodMap));
        this.functionAfter = functionAfter;
        this.functionBefore = functionBefore;
        this.argsMap = argsMap;
    }

    public static void main(String[] args) {
        MiraiEventChannel$Proxy miraiEventChannel$Proxy = new MiraiEventChannel$Proxy(new HashMap<>());
        miraiEventChannel$Proxy.setInvokeAfter(() -> {
            System.out.println("调用之后");
        });
        miraiEventChannel$Proxy.setInvokeBefore(() -> {
            System.out.println("调用之前");
        });
        EventChannelMethodInvokeInter methodInvoker = miraiEventChannel$Proxy.getProxy();
        methodInvoker.subscribeAlways(NumeronBot.INSTANCE.getBot().getEventChannel(), MessageEvent.class, messageEvent -> {
        });
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

    private record EventChannelMethodInvokeImpl() implements EventChannelMethodInvokeInter {

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
            return channel.subscribe(eventClass, coroutineContext, concurrency, priority, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, Function<E, ListeningStatus> handler) {
            return channel.subscribe(eventClass, coroutineContext, handler);
        }

        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, Function<E, ListeningStatus> handler) {
            return channel.subscribe(eventClass, coroutineContext, handler);
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
        private Object target;

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
