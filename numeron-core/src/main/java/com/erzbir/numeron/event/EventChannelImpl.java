package com.erzbir.numeron.event;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Erzbir
 * @Data: 2023/12/6 13:39
 */
@Slf4j
public class EventChannelImpl<E extends Event> extends EventChannel<E> {
    private final ListenerInvoker listenerInvoker = new InterceptorInvoker(listenerInterceptors);
    // 为了避免监听器逻辑中出现阻塞, 从而导致监听无法取消
    private final Map<Listener<?>, Thread> taskMap = new WeakHashMap<>();
    protected List<ListenerDescription> listeners = new ArrayList<>();


    public EventChannelImpl(Class<E> baseEventClass) {
        super(baseEventClass);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void broadcast(EventContext eventContext) {
        Event event = eventContext.getEvent();
        if (!(event instanceof AbstractEvent)) throw new IllegalArgumentException("Event must extend AbstractEvent");
        if (event.isIntercepted()) {
            log.debug("Event: {} was truncated, cancel broadcast", event);
            return;
        }
        callListeners((E) event);
    }

    @SuppressWarnings({"unchecked"})
    protected ListenerHandle registerListener(Class<E> eventType, Listener<E> listener) {
        Listener<E> safeListener = createSafeListener(listener);
        listeners.add(new ListenerDescription((Class<Event>) eventType, safeListener));
        return new WeakReferenceListenerHandle(listener, listeners, createHandleHook(safeListener));
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends E> ListenerHandle subscribe(Class<T> eventType, Function<T, ListenerResult> handle) {
        Listener<E> listener = createListener((Function<E, ListenerResult>) handle);
        return registerListener((Class<E>) eventType, listener);
    }

    @Override
    public <T extends E> ListenerHandle subscribeOnce(Class<T> eventType, Consumer<T> handle) {
        return subscribe(eventType, event -> {
            handle.accept(event);
            return StandardListenerResult.STOP;
        });
    }

    @Override
    public <T extends E> ListenerHandle subscribeAlways(Class<T> eventType, Consumer<T> handle) {
        return subscribe(eventType, event -> {
            handle.accept(event);
            return StandardListenerResult.CONTINUE;
        });
    }


    @Override
    public Listener<E> createListener(Function<E, ListenerResult> handle) {
        return createSafeListener(handle::apply);
    }

    private Listener<E> createSafeListener(Listener<E> listener) {
        if (listener instanceof SafeListener) {
            return listener;
        }
        return new SafeListener(listener);
    }


    @Override
    public EventChannel<E> filter(Predicate<Event> predicate) {
        return new FilterEventChannel<>(this, predicate);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T extends E> EventChannel<T> filterInstance(Class<T> eventType) {
        FilterEventChannel filterEventChannel = new FilterEventChannel(this, eventType);
        filterEventChannel.baseEventClass = eventType;
        return filterEventChannel;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Iterable<Listener<E>> getListeners() {
        return (Iterable) listeners.stream().map(ListenerDescription::listener).toList();
    }

    private void callListeners(E event) {
        for (ListenerDescription listenerDescription : listeners) {
            if (!listenerDescription.eventType().isInstance(event)) {
                continue;
            }
            process(listenerDescription, event);
        }
    }

    private void process(ListenerDescription listenerDescription, E event) {
        Listener<?> listener = listenerDescription.listener();
        log.debug("Broadcasting event: {} to listener: {}", event, listener.getClass().getSimpleName());
        Thread invokeThread = Thread.ofVirtual()
                .name("Listener-Invoke-Thread")
                .unstarted(createInvokeRunnable(event, listener));
        invokeThread.start();
        taskMap.put(listener, invokeThread);
    }

    private Runnable createInvokeRunnable(E event, Listener<?> listener) {
        return () -> {
            try {
                if (!activated.get()) {
                    Thread.currentThread().interrupt();
                    return;
                }
                ListenerResult listenerResult = listenerInvoker.invoke(new DefaultListenerContext(new DefaultEventContext(event), listener));
                if (!listenerResult.isContinue()) {
                    Thread.currentThread().interrupt();
                }
            } catch (Throwable e) {
                log.error("Calling listener error: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        };
    }

    private Runnable createHandleHook(Listener<E> listener) {
        return () -> {
            Thread thread = taskMap.get(listener);
            if (thread == null) {
                return;
            }
            thread.interrupt();
        };
    }

    public class SafeListener implements Listener<E> {
        private final Listener<E> delegate;

        public SafeListener(Listener<E> delegate) {
            this.delegate = delegate;
        }

        @Override
        public ListenerResult onEvent(E event) {
            try {
                return delegate.onEvent(event);
            } catch (Exception e) {
                return StandardListenerResult.STOP;
            }
        }
    }
}
