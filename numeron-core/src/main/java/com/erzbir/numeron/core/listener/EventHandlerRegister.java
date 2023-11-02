package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.config.NumeronBotConfiguration;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 * 事件监听注册器
 * </p>
 *
 * @author Erzbir
 * @Date 2023/7/27
 */
public class EventHandlerRegister implements HandlerRegister {
    public static EventHandlerRegister INSTANCE = new EventHandlerRegister();
    private final DefaultListenerRegisterImpl defaultListenerRegister = new DefaultListenerRegisterImpl();

    @Override
    public void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrency, CoroutineContext coroutineContext) {
        Parameter[] parameters = method.getParameters();
        Class<? extends Event> eventType = parameters[0].getType().asSubclass(Event.class);
        defaultListenerRegister.subscribe(channel, eventType, coroutineContext, concurrency, eventPriority, event -> {
            EventMethodExecute.INSTANCE.execute(method, bean, event);
            return event instanceof BotEvent event1 ? (((NumeronBotConfiguration) event1.getBot().getConfiguration()).isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED) : ListeningStatus.LISTENING;
        });
    }
}
