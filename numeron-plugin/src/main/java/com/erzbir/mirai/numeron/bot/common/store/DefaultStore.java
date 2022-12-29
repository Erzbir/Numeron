package com.erzbir.mirai.numeron.bot.common.store;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2022/11/30 10:40
 */
public final class DefaultStore {
    private static final Object key = new Object();
    private static volatile DefaultStore INSTANCE;
    private final ExpiringMap<Integer, MessageChain> data =
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

    public void save(int id, MessageChain message) {
        data.put(id, message);
    }

    @Nullable
    public MessageChain find(int id) {
        return data.get(id);
    }
}
