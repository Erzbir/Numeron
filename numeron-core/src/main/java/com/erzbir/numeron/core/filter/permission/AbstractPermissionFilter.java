package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.MessageEventFilterInter;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * <p>抽象权限过滤类, 根据规则过滤channel</p>
 */
public abstract class AbstractPermissionFilter implements MessageEventFilterInter, ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event);

    public <E extends Event> Boolean filter(E event) {
        return filter((MessageEvent) event);
    }

    public <E extends Event> EventChannel<E> filter(EventChannel<E> channel) {
        return channel.filter(this::filter);
    }
}
