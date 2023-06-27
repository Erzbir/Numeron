package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息事件过滤装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:39
 * @see AbstractBotEventFilter
 * @see EventFilter
 * @see MessageEvent
 */
public abstract class AbstractMessageEventFilter extends AbstractBotEventFilter<MessageEvent> implements EventFilter<MessageEvent>, Filter {
    protected long id = 0;

    public AbstractMessageEventFilter(Filter filter) {
        super(filter);
    }

    public long getId() {
        return id;
    }

    public AbstractMessageEventFilter setId(long id) {
        this.id = id;
        return this;
    }


}
