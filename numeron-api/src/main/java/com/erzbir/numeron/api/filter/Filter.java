package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.Event;

/**
 * <p>
 * 顶层过滤器父接口, 最终以一个 event 为单位过滤
 * </p>
 *
 * @author Erzbir
 * @Data 2023/7/24
 */
@FunctionalInterface
public interface Filter<E extends Event> {
    boolean filter(E event);
}
