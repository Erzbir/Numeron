package com.erzbir.mirai.numeron.processor.excute;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:31
 */
public class UserMessageMethodExecute implements MethodExecute {
    public static final UserMessageMethodExecute INSTANCE = new UserMessageMethodExecute();

    private UserMessageMethodExecute() {

    }

    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel) {
        new Thread(() -> channel.subscribeAlways(UserMessageEvent.class, event -> {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        })).start();
    }
}
