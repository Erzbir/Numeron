package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Data 2023/7/26
 */
@FunctionalInterface
public interface EnumFilterFactory extends FilterFactory {
    ChannelFilter<? extends Event> create(Enum<?> e);
}
