package com.erzbir.numeron.event;


import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Erzbir
 * @Data: 2023/12/6 16:41
 */
public class FilterEventChannel<E extends Event> extends EventChannel<E> {
    private final EventChannel<E> delegate;
    private final Predicate<Event> filter;

    public FilterEventChannel(EventChannel<E> delegate, Predicate<Event> filter) {
        super(delegate.getBaseEventClass());
        this.delegate = delegate;
        this.filter = filter;
    }

    public FilterEventChannel(EventChannel<E> delegate, Class<E> eventType) {
        this(delegate, eventType::isInstance);
    }


    @Override
    public void broadcast(EventContext eventContext) {
        Event event = eventContext.getEvent();
        if (!baseEventClass.isInstance(event) && filter.test(event)) {
            return;
        }
        delegate.broadcast(eventContext);
    }

    @Override
    protected ListenerHandle registerListener(Class<E> eventType, Listener<E> listener) {
        return delegate.registerListener(eventType, intercept(listener));
    }

    @Override
    public <T extends E> ListenerHandle subscribe(Class<T> eventType, Function<T, ListenerResult> handler) {
        return delegate.subscribe(eventType, intercept(handler));
    }

    @Override
    public <T extends E> ListenerHandle subscribeOnce(Class<T> eventType, Consumer<T> handler) {
        return delegate.subscribeOnce(eventType, intercept(handler));
    }

    @Override
    public <T extends E> ListenerHandle subscribeAlways(Class<T> eventType, Consumer<T> handler) {
        return delegate.subscribeAlways(eventType, intercept(handler));
    }

    @Override
    public Listener<E> createListener(Function<E, ListenerResult> handler) {
        return delegate.createListener(intercept(handler));
    }

    private <T extends E> Consumer<T> intercept(Consumer<T> handler) {
        return (ev) -> {
            boolean filterResult;
            filterResult = getBaseEventClass().isInstance(ev) && filter.test(ev);
            if (filterResult) {
                handler.accept(ev);
            }
        };
    }

    private <T extends E> Listener<T> intercept(Listener<T> listener) {
        return (ev) -> {
            boolean filterResult;
            filterResult = getBaseEventClass().isInstance(ev) && filter.test(ev);
            if (filterResult) {
                return listener.onEvent(ev);
            } else {
                return StandardListenerResult.TRUNCATED;
            }
        };
    }

    private <T extends E> Function<T, ListenerResult> intercept(Function<T, ListenerResult> handler) {
        return (ev) -> {
            boolean filterResult;
            filterResult = getBaseEventClass().isInstance(ev) && filter.test(ev);
            if (filterResult) {
                return handler.apply(ev);
            } else {
                return StandardListenerResult.TRUNCATED;
            }
        };
    }

    @Override
    public EventChannel<E> filter(Predicate<Event> predicate) {
        return delegate.filter(predicate);
    }

    @Override
    public <T extends E> EventChannel<T> filterInstance(Class<T> eventType) {
        return delegate.filterInstance(eventType);
    }

    @Override
    public void addInterceptor(ListenerInterceptor eventInterceptor) {
        delegate.addInterceptor(eventInterceptor);
    }

    @Override
    public Iterable<Listener<E>> getListeners() {
        return delegate.getListeners();
    }
}
