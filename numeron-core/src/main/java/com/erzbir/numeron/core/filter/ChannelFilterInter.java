package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:18
 * <p>
 * 过滤器接口
 * </p>
 */
public interface ChannelFilterInter {

    /**
     * @param event 事件
     * @return Boolean
     */
    <E extends Event> Boolean filter(E event);

    <E extends Event> EventChannel<E> filter(EventChannel<E> channel);
}
