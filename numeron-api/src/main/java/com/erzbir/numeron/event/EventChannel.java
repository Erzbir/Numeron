package com.erzbir.numeron.event;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 事件通道, 提供 监听器 的注册接口以及容器.
 * 通过 broadcast 方法将事件广播给所有监听器
 * <p></p>
 * 所有请求委托到 `EventChannelDispatcher` 中
 * <p></p>
 * 支持 filter 过滤出一个新的 channel
 *
 * @author Erzbir
 * @Data: 2023/12/6 10:46
 */
public abstract class EventChannel<E extends Event> implements ListenerContainer<E>, Cancelable, AutoCloseable {
    protected Class<E> baseEventClass;
    protected List<ListenerInterceptor> listenerInterceptors = new ArrayList<>();
    protected AtomicBoolean activated = new AtomicBoolean(true);

    public EventChannel(Class<E> baseEventClass) {
        this.baseEventClass = baseEventClass;
    }

    public Class<E> getBaseEventClass() {
        return baseEventClass;
    }

    public abstract void broadcast(EventContext eventContext);

    protected abstract ListenerHandle registerListener(Class<E> eventType, Listener<E> listener);

    public abstract <T extends E> ListenerHandle subscribe(Class<T> eventType, Function<T, ListenerResult> handle);

    public abstract <T extends E> ListenerHandle subscribeOnce(Class<T> eventType, Consumer<T> handle);

    public abstract <T extends E> ListenerHandle subscribeAlways(Class<T> eventType, Consumer<T> handle);

    public abstract Listener<E> createListener(Function<E, ListenerResult> handle);

    public abstract EventChannel<E> filter(Predicate<Event> predicate);

    public abstract <T extends E> EventChannel<T> filterInstance(Class<T> eventType);

    public void addInterceptor(ListenerInterceptor listenerInterceptor) {
        listenerInterceptors.add(listenerInterceptor);
    }

    public void close() {
        activated.set(false);
    }

    public void close(Runnable hook, boolean isAsync) {
        if (isAsync) {
            CompletableFuture.runAsync(hook);
        } else {
            hook.run();
        }
    }

    public void open() {
        activated.set(true);
    }

    @Override
    public void cancel() {
        close();
    }

    @Override
    public boolean isCanceled() {
        return !activated.get();
    }
}
