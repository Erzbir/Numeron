package com.erzbir.numeron.common;

import java.util.LinkedHashMap;

/**
 * @author Erzbir
 * @Data: 2024/2/7 17:46
 */
public class MapAttribute<K, V> extends LinkedHashMap<K, V> implements AttributeContainer<K, V> {
    @Override
    public void putAttribute(K key, V value) {
        put(key, value);
    }

    @Override
    public void putIfAbsentAttribute(K key, V value) {
        putIfAbsent(key, value);
    }

    @Override
    public V removeAttribute(K key) {
        return remove(key);
    }

    @Override
    public boolean removeAttribute(K key, V value) {
        return remove(key, value);
    }

    @Override
    public V getAttribute(K key) {
        return get(key);
    }

    @Override
    public V getIfAbsentAttribute(K key) {
        return get(key);
    }
}
