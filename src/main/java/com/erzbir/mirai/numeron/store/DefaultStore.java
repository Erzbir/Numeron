package com.erzbir.mirai.numeron.store;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2022/11/30 10:40
 */
public class DefaultStore {
    public static volatile DefaultStore INSTANCE;
    private static final Object key = new Object();
    private static final ExpiringMap<Integer, MessageChain> data =
            ExpiringMap.builder()
                    .maxSize(1000)
                    .expiration(86400L, TimeUnit.SECONDS)
                    .expirationPolicy(ExpirationPolicy.ACCESSED)
                    .variableExpiration().build();

    private DefaultStore() {
    }

    public static DefaultStore getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new DefaultStore();
                }
            }
        }
        return INSTANCE;
    }

    public final void save(int id, MessageChain message) {
        data.put(id, message);
    }

    @Nullable
    public final MessageChain find(int id) {
        return data.get(id);
    }
}
