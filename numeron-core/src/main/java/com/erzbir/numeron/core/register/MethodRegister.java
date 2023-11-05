package com.erzbir.numeron.core.register;

import com.erzbir.numeron.annotation.Handler;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public interface MethodRegister {
    default void registerMethods(Object bean, EventChannel<? extends Event> channel, CoroutineContext coroutineContext) {
        Arrays.stream(bean.getClass().getDeclaredMethods()).filter(method -> method.getAnnotation(Handler.class) != null).forEach(method -> registerMethod(bean, method, channel, coroutineContext));
    }

    void registerMethod(Object bean, Method method, EventChannel<? extends Event> channel, CoroutineContext coroutineContext);
}
