package com.erzbir.numeron.core.handler.excute;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.exception.ErrorReporter;
import com.erzbir.numeron.core.listener.ListenerContext;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2023/3/9 22:57
 */
public interface RegisterEventHandle {
    default void register(EventChannel<BotEvent> channel, Class<? extends BotEvent> eventType, Method method, Object bean) {
        Listener<? extends BotEvent> subscribe = channel.subscribe(eventType, event1 -> {
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
