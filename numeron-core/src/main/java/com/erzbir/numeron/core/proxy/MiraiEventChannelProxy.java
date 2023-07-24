package com.erzbir.numeron.core.proxy;

import com.erzbir.numeron.api.listener.ListenerRegisterInter;
import com.erzbir.numeron.core.handler.excute.EventMethodExecute;
import com.erzbir.numeron.core.processor.MessageAnnotationProcessor;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
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

    public MiraiEventChannelProxy() {
        this(() -> {
        }, () -> {
        }, new HashMap<>());
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


    public interface EventChannelMethodInvokeInter extends ListenerRegisterInter {

    }

    private static class EventChannelMethodInvokeImpl implements EventChannelMethodInvokeInter {
        private static final ExecutorService executor = Executors.newCachedThreadPool();


        @Override
        public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
            return channel.subscribe(eventClass, coroutineContext, concurrency, priority, handler);
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

        @Override
        public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost) {
            channel.registerListenerHost(listenerHost);
        }

        @Override
        public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext) {
            channel.registerListenerHost(listenerHost, coroutineContext);
        }

        @Override
        public void setRunBefore(Runnable before) {
            EventMethodExecute.INSTANCE.executeBefore(before);
        }

        @Override
        public void setRunAfter(Runnable after) {
            EventMethodExecute.INSTANCE.executeAfter(after);
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
