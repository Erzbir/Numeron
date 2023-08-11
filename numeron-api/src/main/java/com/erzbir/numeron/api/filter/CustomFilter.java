package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date 2023/8/11
 * <p>
 * 实现此接口以供自定义过滤使用
 * </p>
 */
@FunctionalInterface
public interface CustomFilter<E extends Event> extends ChannelFilter<E> {
}
