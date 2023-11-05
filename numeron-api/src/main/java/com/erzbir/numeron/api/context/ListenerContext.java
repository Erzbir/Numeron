package com.erzbir.numeron.api.context;

import net.mamoe.mirai.event.Listener;

/**
 * @author Erzbir
 * @Date 2023/11/2
 */
public interface ListenerContext extends Context {
    void register(Listener<?> listener);

    Listener<?> removeListener(String name);

    Listener<?> getListener(String name);

    void cancel(String name);
}