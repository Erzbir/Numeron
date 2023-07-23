package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Data 2023/7/23
 */
public interface CacheFilterFactory extends FilterFactory {
    Filter<? extends Event> getFilter(Class<? extends Filter<? extends Event>> filerClass);
}
