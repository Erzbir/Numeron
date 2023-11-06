package com.erzbir.numeron.core.register;

import com.erzbir.numeron.config.NumeronBotConfiguration;
import kotlin.coroutines.CoroutineContext;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class EventHandlerRegister implements HandlerRegister {
    private EventMethodExecute eventMethodExecute = new EventMethodExecute();

    @Override
    public void register(Object bean, Method method, EventChannel<? extends Event> channel, EventPriority eventPriority, ConcurrencyKind concurrency, CoroutineContext coroutineContext) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
            return;
        }
        Class<? extends Event> eventType = parameters[0].getType().asSubclass(Event.class);
        channel.subscribe(eventType, coroutineContext, concurrency, eventPriority, event -> {
            eventMethodExecute.execute(method, bean, event);
            return event instanceof BotEvent event1 ? (((NumeronBotConfiguration) event1.getBot().getConfiguration()).isEnable() ? ListeningStatus.LISTENING : ListeningStatus.STOPPED) : ListeningStatus.LISTENING;
        });
    }
}
