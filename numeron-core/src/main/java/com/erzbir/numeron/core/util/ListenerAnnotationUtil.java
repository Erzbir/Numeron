package com.erzbir.numeron.core.util;

import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date 2023/9/25
 */
public class ListenerAnnotationUtil {
    public static Method[] getCallBackMethods(Object bean) {
        Class<?> aClass = bean.getClass();
        List<Method> methods = new ArrayList<>();
        if (!aClass.isAnnotationPresent(Listener.class) && !ListenerHost.class.isAssignableFrom(aClass)) {
            return null;
        }
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Handler.class) || method.isAnnotationPresent(EventHandler.class)) {
                methods.add(method);
            }
        }
        return methods.toArray(new Method[]{});
    }
}
