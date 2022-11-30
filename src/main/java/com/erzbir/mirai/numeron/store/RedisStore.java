package com.erzbir.mirai.numeron.store;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Date: 2022/11/30 10:32
 */
public class RedisStore {
    public static volatile RedisStore INSTANCE;
    private static final Object key = new Object();
    private final Jedis client = new Jedis("localhost", 6379);

    public final void set(@NotNull String key, @NotNull String value, long seconds) {
        if (seconds == -1L) {
            client.set(key, value);
        } else {
            client.setex(key, seconds, value);
        }

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

    public final void del(@NotNull String key) {
        client.del(key);
    }

    public final String get(@NotNull String key) {
        return client.get(key);
    }

    public final @NotNull List<String> getPic() {
        Set<String> keys = client.keys("pic_*");
        List<String> list = new ArrayList<>();
        keys.forEach(v -> list.add(client.get(v)));
        return list;
    }

    public final @NotNull List<String> getFile() {
        Set<String> keys = client.keys("file_*");
        List<String> list = new ArrayList<>();
        keys.forEach(v -> list.add(client.get(v)));
        return list;
    }

    public final @NotNull List<String> getPlain() {
        Set<String> keys = client.keys("plain_*");
        List<String> list = new ArrayList<>();
        keys.forEach(v -> list.add(client.get(v)));
        return list;
    }


    public final void removePic(@NotNull String filename) {
        Stream<String> stringStream = client.keys("pic_*").stream().filter(v -> Objects.equals(v, filename));
        stringStream.forEach(client::del);
    }

    public final void removeFile(@NotNull String filename) {
        Stream<String> stringStream = client.keys("file_*").stream().filter(v -> Objects.equals(v, filename));
        stringStream.forEach(client::del);
    }

    public final void removePlain(@NotNull String filename) {
        Stream<String> stringStream = client.keys("plain_*").stream().filter(v -> Objects.equals(v, filename));
        stringStream.forEach(client::del);

    }

    public final void useClient(@NotNull Function1<Jedis, Unit> function1) {
        function1.invoke(client);
    }
}
