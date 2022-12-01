package com.erzbir.mirai.numeron.store;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.io.IOException;
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
    private static final Object key = new Object();
    private static volatile RedisStore INSTANCE;

    static {
        try {
            new ProcessBuilder().command("redis-cli").start().destroyForcibly();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Jedis client = new Jedis("localhost", 6379);

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

    public final void set(@NotNull String key, @NotNull String value, long seconds) {
        if (seconds == -1L) {
            client.set(key, value);
        } else {
            client.setex(key, seconds, value);
        }

    }

    public final void del(@NotNull String key) {
        client.del(key);
    }

    public final String get(@NotNull String key) {
        return client.get(key);
    }

    public final @NotNull List<String> getPic() throws Exception {
        Set<String> keys = null;
        try {
            keys = client.keys("pic_*");
        } catch (Exception e) {
            throw new Exception(e);
        }
        List<String> list = new ArrayList<>();
        if (keys != null) {
            keys.forEach(v -> list.add(client.get(v)));
        }
        return list;
    }

    public final @NotNull List<String> getFile() throws Exception {
        Set<String> keys;
        try {
            keys = client.keys("file_*");
        } catch (Exception e) {
            throw new Exception(e);
        }
        List<String> list = new ArrayList<>();
        if (keys != null) {
            keys.forEach(v -> list.add(client.get(v)));
        }
        return list;
    }

    public final @NotNull List<String> getPlain() throws Exception {
        Set<String> keys;
        try {
            keys = client.keys("plain_*");
        } catch (Exception e) {
            throw new Exception(e);
        }
        List<String> list = new ArrayList<>();
        if (keys != null) {
            keys.forEach(v -> list.add(client.get(v)));
        }
        return list;
    }


    public final void removePic(@NotNull String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("pic_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);
    }

    public final void removeFile(@NotNull String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("file_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);
    }

    public final void removePlain(@NotNull String filename) throws Exception {
        Stream<String> stringStream;
        try {
            stringStream = client.keys("plain_*").stream().filter(v -> Objects.equals(v, filename));
        } catch (Exception e) {
            throw new Exception(e);
        }
        stringStream.forEach(client::del);

    }

    public final void useClient(@NotNull Function1<Jedis, Unit> function1) {
        function1.invoke(client);
    }
}
