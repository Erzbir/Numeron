package com.erzbir.numeron.core.handler.excute;

import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 * <p>消息处理方法</p>
 */
public class EventMethodExecute implements MethodExecute {
    public static final EventMethodExecute INSTANCE = new EventMethodExecute();

    private EventMethodExecute() {
    }

    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel, Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Annotation> aClass = annotation.annotationType();
        EventPriority priority = (EventPriority) aClass.getDeclaredMethod("priority").invoke(annotation);
        ConcurrencyKind concurrency = (ConcurrencyKind) aClass.getDeclaredMethod("concurrency").invoke(annotation);
        Parameter[] parameters = method.getParameters();
        Class<? extends Event> eventType = parameters[0].getType().asSubclass(Event.class);
        ListenerContext.INSTANCE.getListenerRegister().subscribe(channel, eventType, EmptyCoroutineContext.INSTANCE, concurrency, priority, (Function<Event, ListeningStatus>) event -> {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                NumeronLogUtil.logger.error(e);
                e.printStackTrace();
            }
            return NumeronBot.INSTANCE.isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED;
        });
    }
}
