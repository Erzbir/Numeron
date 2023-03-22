package com.erzbir.numeron.core.handler.excute;

import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 * <p>消息处理方法</p>
 */
public class EventMethodExecute implements MethodExecute, RegisterEventHandle {
    public static final EventMethodExecute INSTANCE = new EventMethodExecute();

    private EventMethodExecute() {
    }

    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel, Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Annotation> aClass = annotation.annotationType();
        EventPriority priority = (EventPriority) aClass.getDeclaredMethod("priority").invoke(annotation);
        ConcurrencyKind concurrency = (ConcurrencyKind) aClass.getDeclaredMethod("concurrency").invoke(annotation);
        register(channel, method, bean, concurrency, priority);
    }
}
