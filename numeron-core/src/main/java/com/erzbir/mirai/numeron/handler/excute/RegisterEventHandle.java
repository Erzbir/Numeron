package com.erzbir.mirai.numeron.handler.excute;

import com.erzbir.mirai.numeron.exception.ErrorReporter;
import com.erzbir.mirai.numeron.listener.ListenerContext;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2023/3/9 22:57
 */
public interface RegisterEventHandle {
    default void register(EventChannel<BotEvent> channel, Class<? extends MessageEvent> eventType, Method method, Object bean) {
        Listener<? extends MessageEvent> listener = channel.subscribeAlways(eventType, event1 -> {
            try {
                method.invoke(bean, event1);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorReporter.save(e);
            }
        });
        ListenerContext.INSTANCE.add(method, listener);
    }
}
