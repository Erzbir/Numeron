package com.erzbir.numeron.core.handler.excute;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.exception.ErrorReporter;
import com.erzbir.numeron.core.listener.ListenerContext;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Erzbir
 * @Date: 2023/3/9 22:57
 */
public interface RegisterEventHandle {
    default void register(EventChannel<BotEvent> channel, Method method, Object bean, ConcurrencyKind concurrencyKind, EventPriority eventPriority) {
        Parameter[] parameters = method.getParameters();
        Listener<? extends BotEvent> subscribe = channel.subscribe(parameters[0].getType().asSubclass(BotEvent.class), EmptyCoroutineContext.INSTANCE, concurrencyKind, eventPriority, event1 -> {
            try {
                method.invoke(bean, event1);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorReporter.save(e);
            }
            return NumeronBot.INSTANCE.isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED;
        });
        ListenerContext.INSTANCE.add(method, subscribe);
    }
}
