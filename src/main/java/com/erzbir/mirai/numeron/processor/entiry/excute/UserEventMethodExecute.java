package com.erzbir.mirai.numeron.processor.entiry.excute;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.UserEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 */
public class UserEventMethodExecute implements MethodExecute {
    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel) {
        new Thread(() -> channel.subscribeAlways(UserEvent.class, event -> {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        })).start();
    }
}
