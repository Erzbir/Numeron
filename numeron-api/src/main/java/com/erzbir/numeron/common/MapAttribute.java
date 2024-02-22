package com.erzbir.numeron.common;

import java.util.LinkedHashMap;

/**
 * 基于 {@link LinkedHashMap}
 *
 * @author Erzbir
 * @Data: 2024/2/7 17:46
 */
public class MapAttribute<K, V> extends LinkedHashMap<Attribute.Key<K>, Attribute<K, V>> implements AttributeContainer<K, V> {
    @Override
    public void putAttribute(Attribute<K, V> attribute) {
        put(attribute.getKey(), attribute);
    }

    @Override
    public void putIfAbsentAttribute(Attribute<K, V> attribute) {
        putIfAbsent(attribute.getKey(), attribute);
    }

    @Override
    public Attribute<K, V> removeAttribute(Attribute.Key<K> key) {
        return remove(key);
    }

    @Override
    public boolean removeAttribute(Attribute.Key<K> key, Attribute<K, V> attribute) {
        return remove(key, attribute);
    }

    @Override
    public Attribute<K, V> getAttribute(Attribute.Key<K> key) {
        return get(key);
    }

    @Override
    public boolean containsAttribute(Attribute<K, V> attribute) {
        return containsKey(attribute.getKey());
    }
}
