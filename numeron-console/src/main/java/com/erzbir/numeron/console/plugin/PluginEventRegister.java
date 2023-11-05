package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.api.context.DefaultBeanCentral;
import com.erzbir.numeron.core.register.DefaultMethodRegister;
import com.erzbir.numeron.core.register.MethodRegister;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/30 11:59
 */
public class PluginEventRegister implements PluginRegister {
    private MethodRegister methodRegister = new DefaultMethodRegister();

    @Override
    public void register(PluginContext pluginContext) {
        EventChannel<BotEvent> eventChannel = GlobalEventChannel.INSTANCE
                .filterIsInstance(BotEvent.class);
        pluginContext.getClasses().stream().filter(t -> t.getAnnotation(Listener.class) != null).forEach(t -> {
            DefaultBeanCentral.INSTANCE.plus(t);
            methodRegister.registerMethods(t, eventChannel, EmptyCoroutineContext.INSTANCE);
        });
    }
}
