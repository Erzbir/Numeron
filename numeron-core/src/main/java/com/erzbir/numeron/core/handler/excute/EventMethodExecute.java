package com.erzbir.numeron.core.handler.excute;

import com.erzbir.numeron.api.bot.NumeronBotConfiguration;
import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 * <p>消息处理方法</p>
 */
public class EventMethodExecute implements MethodExecute {
    public static final EventMethodExecute INSTANCE = new EventMethodExecute();
    private Runnable before = () -> {
    };
    private Runnable after = () -> {
    };

    private EventMethodExecute() {
    }

    @Override
    public void executeBefore(Runnable runnable) {
        before = runnable == null ? before : runnable;
    }

    @Override
    public void executeAfter(Runnable runnable) {
        after = runnable == null ? before : runnable;
    }

    @Override
    public void execute(Method method, Object bean, EventChannel<Event> channel, Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Annotation> aClass = annotation.annotationType();
        EventPriority priority = (EventPriority) aClass.getDeclaredMethod("priority").invoke(annotation);
        ConcurrencyKind concurrency = (ConcurrencyKind) aClass.getDeclaredMethod("concurrency").invoke(annotation);
        Parameter[] parameters = method.getParameters();
        Class<? extends Event> eventType = parameters[0].getType().asSubclass(Event.class);
        ListenerContext.INSTANCE.getListenerRegister().subscribe(channel, eventType, EmptyCoroutineContext.INSTANCE, concurrency, priority, event -> {
            try {
                before.run();
                method.invoke(bean, event);
                after.run();
            } catch (IllegalAccessException | InvocationTargetException e) {
                NumeronLogUtil.logger.error("ERROR", e);
            }
            return event instanceof BotEvent event1 ? (((NumeronBotConfiguration) event1.getBot().getConfiguration()).isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED) : ListeningStatus.LISTENING;
        });
    }
}
