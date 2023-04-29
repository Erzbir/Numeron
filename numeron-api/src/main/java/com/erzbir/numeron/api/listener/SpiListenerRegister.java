package com.erzbir.numeron.api.listener;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/4/29 10:14
 */
public final class SpiListenerRegister {
    public static EventListenerRegister GlobalListenerRegister;
    public static EventListenerRegister BotListenerRegister;
    public static EventListenerRegister PluginListenerRegister;

    static {
        for (EventListenerRegister register : ServiceLoader.load(EventListenerRegister.class)) {
            String name = register.getClass().getName();
            if (name.startsWith("G")) {
                GlobalListenerRegister = register;
            } else if (name.startsWith("B")) {
                BotListenerRegister = register;
            } else if (name.startsWith("P")) {
                PluginListenerRegister = register;
            }
        }
    }
}
