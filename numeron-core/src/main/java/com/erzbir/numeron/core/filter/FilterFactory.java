package com.erzbir.numeron.core.filter;


import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date: 2022/11/26 17:54
 */
@FunctionalInterface
public interface FilterFactory {
    Filter<? extends Event> create(Enum<?> e);
}
