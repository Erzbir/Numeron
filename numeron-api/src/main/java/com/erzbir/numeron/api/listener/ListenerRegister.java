package com.erzbir.numeron.api.listener;


import com.erzbir.numeron.utils.NumeronLogUtil;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/5/2 02:44
 */
public class ListenerRegister {
    public static final ListenerRegister INStANCE = new ListenerRegister();
    public EventListenerRegister Global = SpiListenerRegisterLoader.GlobalListenerRegister;
    public EventListenerRegister Bot = SpiListenerRegisterLoader.BotListenerRegister;
    public EventListenerRegister Plugin = SpiListenerRegisterLoader.PluginListenerRegister;

    private static final class SpiListenerRegisterLoader {
        static EventListenerRegister GlobalListenerRegister;
        static EventListenerRegister BotListenerRegister;
        static EventListenerRegister PluginListenerRegister;

        static {
            for (EventListenerRegister register : ServiceLoader.load(EventListenerRegister.class)) {
                String name = register.getClass().getName();
                switch (name.charAt(0)) {
                    case 'G' -> GlobalListenerRegister = register;
                    case 'B' -> BotListenerRegister = register;
                    case 'P' -> PluginListenerRegister = register;
                    default -> {
                        NumeronLogUtil.logger.error("no impl");
                        throw new RuntimeException("no impl");
                    }
                }
            }
        }
    }
}
