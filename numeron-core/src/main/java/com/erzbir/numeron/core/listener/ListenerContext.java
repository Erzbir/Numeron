package com.erzbir.numeron.core.listener;

import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CancellationException;

/**
 * @author Erzbir
 * @Date: 2023/3/17 23:26
 * <p>
 * 监听器上下文
 * </p>
 */
public class ListenerContext {
    public static final ListenerContext INSTANCE = new ListenerContext();
    private final HashMap<Method, Listener<? extends BotEvent>> context = new HashMap<>();

    public void add(Method method, Listener<? extends BotEvent> listener) {
        context.put(method, listener);
    }

    public Listener<? extends BotEvent> get(Method method) {
        return context.get(method);
    }

    public void cancelOne(Method method) {
        context.get(method).cancel(new CancellationException());
    }

    public void cancelAll() {
        context.forEach((k, v) -> {
            if (v.isActive()) {
                v.cancel(null);
            }
        });
    }
}
