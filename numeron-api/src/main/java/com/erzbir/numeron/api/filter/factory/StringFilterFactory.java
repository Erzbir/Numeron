package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
@FunctionalInterface
public interface StringFilterFactory extends FilterFactory<String> {
    Filter<? extends Event> create(String s);
}
