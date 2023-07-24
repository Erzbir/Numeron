package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Data 2023/7/24
 */
@FunctionalInterface
public interface Filter<E extends Event> {
    boolean filter(E event);
}
