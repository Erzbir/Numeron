package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * 事件过滤抽象装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 16:08
 * @see AbstractDecoratorFilter
 * @see EventFilter
 * @see Event
 */
public abstract class AbstractEventFilter<E extends Event> extends AbstractDecoratorFilter implements EventFilter<E> {
    public AbstractEventFilter(Filter filter) {
        super(filter);
    }

}
