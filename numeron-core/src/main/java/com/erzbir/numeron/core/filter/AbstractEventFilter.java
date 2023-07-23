package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;

/**
 * 事件过滤抽象装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 16:08
 * @see Event
 */
public abstract class AbstractEventFilter implements Filter<Event> {
    public abstract EventChannel<? extends Event> filter(EventChannel<? extends Event> channel);

}
