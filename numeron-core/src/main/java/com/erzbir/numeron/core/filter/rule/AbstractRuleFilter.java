package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.MessageEventFilterInter;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * <p>抽象规则过滤类</p>
 */
public abstract class AbstractRuleFilter implements MessageEventFilterInter, ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event);

    public <E extends Event> Boolean filter(E event) {
        return filter((MessageEvent) event);
    }

    public <E extends Event> EventChannel<E> filter(EventChannel<E> channel) {
        return channel.filter(this::filter);
    }
}
