package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.MessageEventFilterInter;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * <p>
 * 抽象消息过滤类
 * </p>
 */
public abstract class AbstractMessageFilter implements MessageEventFilterInter, ChannelFilterInter {
    protected String text = "";

    @Override
    public abstract Boolean filter(MessageEvent event);

    public <E extends Event> Boolean filter(E event) {
        return filter((MessageEvent) event);
    }

    public <E extends Event> EventChannel<E> filter(EventChannel<E> channel) {
        return channel.filter(this::filter);
    }

    public String getText() {
        return text;
    }

    public AbstractMessageFilter setText(String text) {
        this.text = text;
        return this;
    }
}
