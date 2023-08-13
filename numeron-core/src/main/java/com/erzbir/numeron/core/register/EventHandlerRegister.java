package com.erzbir.numeron.core.register;

import com.erzbir.numeron.bot.NumeronBotConfiguration;
import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.core.handler.excute.EventMethodExecute;
import com.erzbir.numeron.core.listener.DefaultListenerRegisterImpl;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Erzbir
 * @Date 2023/7/27
 * <p>
 * 事件监听注册器
 * </p>
 */
public class EventHandlerRegister implements HandlerRegister {
    public static EventHandlerRegister INSTANCE = new EventHandlerRegister();
    private final DefaultListenerRegisterImpl defaultListenerRegister = new DefaultListenerRegisterImpl();

    @Override
    public void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrency) {
        Parameter[] parameters = method.getParameters();
        Class<? extends Event> eventType = parameters[0].getType().asSubclass(Event.class);
        defaultListenerRegister.subscribe(channel, eventType, EmptyCoroutineContext.INSTANCE, concurrency, eventPriority, event -> {
            EventMethodExecute.INSTANCE.execute(method, bean, event);
            return event instanceof BotEvent event1 ? (((NumeronBotConfiguration) event1.getBot().getConfiguration()).isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED) : ListeningStatus.LISTENING;
        });
    }
}
