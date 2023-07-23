package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息事件过滤装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:39
 * @see AbstractBotEventFilter
 * @see MessageEvent
 */
public abstract class AbstractMessageEventFilter implements Filter<MessageEvent> {
    protected long id = 0;

    public abstract EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel);

    public long getId() {
        return id;
    }

    public AbstractMessageEventFilter setId(long id) {
        this.id = id;
        return this;
    }


}
