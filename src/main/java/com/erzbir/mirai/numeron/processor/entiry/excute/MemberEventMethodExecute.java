package com.erzbir.mirai.numeron.processor.entiry.excute;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMemberEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:30
 */
public class MemberEventMethodExecute implements MethodExecute {
    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel) {
        new Thread(() -> channel.subscribeAlways(GroupMemberEvent.class, event -> {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        })).start();
    }
}
