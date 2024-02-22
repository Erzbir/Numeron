package com.erzbir.numeron.common;

/**
 * @author Erzbir
 * @Data: 2024/2/22 14:43
 */
public interface Attribute<K, V> {
    Key<K> getKey();

    Value<V> getValue();

    Attribute<K, V> setKey(K key);

    Attribute<K, V> setValue(V value);

    static Attribute<Object, Object> empty() {
        return new Builder<>(null, null).build();
    }

    interface Key<E> {
        E getKey();

        Key<E> setKey(E key);
    }

    interface Value<E> {
        E getValue();

        Value<E> setValue(E value);
    }

    class Builder<K, V> {
        Attribute<K, V> attribute;

        public Builder(K key, V value) {
            attribute = new SimpleAttribute(new SimpleKey(key), new SimpleValue(value));
        }

        public Attribute<K, V> build() {
            return attribute;
        }

        private class SimpleKey implements Key<K> {
            K key;

            public SimpleKey(K key) {
                this.key = key;
            }

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public Key<K> setKey(K key) {
                this.key = key;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                return key.equals(o);
            }
        }

        class SimpleValue implements Value<V> {
            V value;

            public SimpleValue(V value) {
                this.value = value;
            }


            @Override
            public V getValue() {
                return value;
            }

            @Override
            public Value<V> setValue(V value) {
                this.value = value;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                return value.equals(o);
            }
        }

        private class SimpleAttribute implements Attribute<K, V> {
            private Key<K> key;
            private Value<V> value;

            public SimpleAttribute(Key<K> key, Value<V> value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public Key<K> getKey() {
                return key;
            }

            @Override
            public Value<V> getValue() {
                return value;
            }

            @Override
            public Attribute<K, V> setKey(K key) {
                this.key.setKey(key);
                return this;
            }

            @Override
            public Attribute<K, V> setValue(V value) {
                this.value.setValue(value);
                return this;
            }
        }
    }
}


