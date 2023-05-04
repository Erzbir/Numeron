package com.erzbir.numeron.core.handler.excute;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:25
 * <p>消息处理接口</p>
 */
public interface MethodExecute {
    void execute(Method method, Object bean, EventChannel<Event> channel, Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
