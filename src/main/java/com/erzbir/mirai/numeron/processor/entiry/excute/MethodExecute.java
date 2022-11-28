package com.erzbir.mirai.numeron.processor.entiry.excute;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:25
 */
public interface MethodExecute {
    void execute(Method method, Object bean, EventChannel<BotEvent> channel);
}
