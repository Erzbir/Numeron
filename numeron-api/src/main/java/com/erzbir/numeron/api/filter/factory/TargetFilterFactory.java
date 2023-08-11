package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.contact.ContactOrBot;
import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
@FunctionalInterface
public interface TargetFilterFactory<E extends ContactOrBot> extends FilterFactory<E> {
    Filter<? extends Event> create(E contactOrBot);
}
