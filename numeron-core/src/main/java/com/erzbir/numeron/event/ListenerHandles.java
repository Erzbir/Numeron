package com.erzbir.numeron.event;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Erzbir
 * @Data: 2024/2/13 16:08
 */
abstract class HookableHandle implements ListenerHandle {
    protected Runnable hook;

    public HookableHandle(Runnable hook) {
        this.hook = hook;
        if (this.hook == null) {
            this.hook = () -> {
            };
        }
    }
}

class IndexListenerHandle extends HookableHandle implements ListenerHandle {
    private final AtomicBoolean disposed = new AtomicBoolean(false);

    private final int index;
    private final List<Listener<?>> listeners;

    public IndexListenerHandle(int index, List<Listener<?>> listeners) {
        this(index, listeners, null);
    }

    public IndexListenerHandle(int index, List<Listener<?>> listeners, Runnable hook) {
        super(hook);
        this.index = index;
        this.listeners = listeners;
    }

    @Override
    public void dispose() {
        if (!disposed.compareAndSet(false, true)) {
            return;
        }
        listeners.remove(index);
        hook.run();
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }
}

class WeakReferenceListenerHandle extends HookableHandle implements ListenerHandle {
    private final AtomicBoolean disposed = new AtomicBoolean(false);
    private WeakReference<Listener<?>> listenerRef;
    private WeakReference<Collection<ListenerDescription>> collectionRef;

    public WeakReferenceListenerHandle(Listener<?> listener, Collection<ListenerDescription> collection) {
        this(listener, collection, null);
    }

    public WeakReferenceListenerHandle(Listener<?> listener, Collection<ListenerDescription> collection, Runnable hook) {
        super(hook);
        this.listenerRef = new WeakReference<>(listener);
        this.collectionRef = new WeakReference<>(collection);
    }


    @Override
    public void dispose() {
        if (!disposed.compareAndSet(false, true)) {
            return;
        }
        Collection<ListenerDescription> collection = collectionRef.get();
        if (collection != null) {
            Listener<?> listener = listenerRef.get();
            if (listener != null) {
                collection.removeIf(listenerDescription -> listenerDescription.listener().equals(listener));
            }
        }
        listenerRef = null;
        collectionRef = null;
        hook.run();
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }
}

