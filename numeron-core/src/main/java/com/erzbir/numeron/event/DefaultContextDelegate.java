package com.erzbir.numeron.event;

import com.erzbir.numeron.common.Context;
import com.erzbir.numeron.common.View;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Data: 2024/2/22 18:14
 */
class DefaultContextDelegate {
    private Context context = Context.empty();

    public Context put(Object key, Object value) {
        return context.put(key, value);
    }

    public View readOnly() {
        return context.readOnly();
    }

    public Context putNonNull(Object key, Object value) {
        context = context.putNonNull(key, value);
        return context;
    }

    public Context delete(Object key) {
        context = context.delete(key);
        return context;
    }

    public <T> T get(Object key) {
        return context.get(key);
    }

    public <T> T get(Class<T> key) {
        return context.get(key);
    }

    public <T> T getOrDefault(Object key, T defaultValue) {
        return context.getOrDefault(key, defaultValue);
    }

    public <T> Optional<T> getOrEmpty(Object key) {
        return context.getOrEmpty(key);
    }

    public boolean hasKey(Object key) {
        return context.hasKey(key);
    }

    public boolean isEmpty() {
        return context.isEmpty();
    }

    public int size() {
        return context.size();
    }

    public Stream<Map.Entry<Object, Object>> stream() {
        return context.stream();
    }

    public void forEach(BiConsumer<Object, Object> action) {
        context.forEach(action);
    }
}
