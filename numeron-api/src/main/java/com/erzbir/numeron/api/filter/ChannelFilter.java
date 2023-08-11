package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;

/**
 * <p>
 * 实现此接口可过滤一个 channel {@link  EventChannel}, 通过实现父接口的抽象方法过滤
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/6/27 11:35
 */
@FunctionalInterface
public interface ChannelFilter<E extends Event> extends Filter<E> {
    default EventChannel<? extends E> filter(EventChannel<? extends E> channel) {
        return channel.filter(this::filter);
    }
}
