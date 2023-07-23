package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.context.AppContextServiceImpl;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Erzbir
 * @Date: 2023/6/30 11:59
 */
//public class PluginEventRegister {
//    public static Listener<Event> register(PluginContext pluginContext) {
//        EventChannel<Event> eventEventChannel = GlobalEventChannel.INSTANCE
//                .filter(it -> it instanceof BotEvent)
//                .parentScope(pluginContext.getPlugin());
//        pluginContext.getClasses().forEach(t -> {
//            Object bean = AppContextServiceImpl.INSTANCE.getBean(t);
//
//        });
//    }
//}
