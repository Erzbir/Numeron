package com.erzbir.mirai.numeron.handler.excute;

import com.erzbir.mirai.numeron.exception.ErrorReporter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2023/3/9 22:57
 */
public interface RegisterEventHandle {
    static void register(EventChannel<BotEvent> channel, Method method, Object bean) {
        channel.subscribeAlways(MessageEvent.class, event -> {
            try {
                method.invoke(bean, event);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorReporter.add(e);
            }
        });
    }
}
