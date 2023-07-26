package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息事件过滤装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:39
 * @see AbstractBotEventChannelFilter
 * @see MessageEvent
 */
public abstract class AbstractMessageEventChannelFilter extends AbstractEventChannelFilter<MessageEvent> implements ChannelFilter<MessageEvent> {
    protected long id = 0;

    public long getId() {
        return id;
    }

    public AbstractMessageEventChannelFilter setId(long id) {
        this.id = id;
        return this;
    }
}
