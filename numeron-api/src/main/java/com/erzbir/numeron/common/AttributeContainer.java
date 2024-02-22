package com.erzbir.numeron.common;

public interface AttributeContainer<K, V> {
    void putAttribute(K key, V value);

    void putIfAbsentAttribute(K key, V value);

    V removeAttribute(K key);

    boolean removeAttribute(K key, V value);

    V getAttribute(K key);

    V getIfAbsentAttribute(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);
}
