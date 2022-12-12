package com.erzbir.mirai.numeron.store;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPooled;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Date: 2022/11/30 10:32
 */
public final class RedisStore {
    private static final Object key = new Object();
    private static final JedisPooled client = new JedisPooled("localhost", 6379);
    private static volatile RedisStore INSTANCE;

    private RedisStore() {

    }

    public static RedisStore getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new RedisStore();
                }
            }
        }
        return INSTANCE;
    }

    public void set(String key, String value, long seconds) {
        if (seconds == -1L) {
            client.set(key, value);
        } else {
            client.setex(key, seconds, value);
        }

    }

    private List<String> toList(Set<String> keys) {
        List<String> list = new ArrayList<>();
        if (keys != null) {
            keys.forEach(v -> list.add(client.get(v)));
        }
        return list;
    }


    public void del(String key) {
        client.del(key);
    }

    public String get(String key) {
        return client.get(key);
    }

    public @NotNull List<String> getPic() {
        return toList(client.keys("pic_*"));
    }

    public @NotNull List<String> getFile() {
        return toList(client.keys("file_*"));
    }

    public @NotNull List<String> getPlain() {
        return toList(client.keys("plain_*"));
    }


    public void removePic(String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("pic_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);
    }

    public void removeFile(@NotNull String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("file_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);
    }

    public void removePlain(String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("plain_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);

    }

    public void useClient(Function1<JedisPooled, Object> function1) {
        function1.invoke(client);
    }
}
