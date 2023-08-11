package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.api.filter.ChannelFilter;
import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefualtFilter;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date 2023/8/11
 */
public class EventAnnotationChannelFilter extends AbstractAnnotationChannelFilter<com.erzbir.numeron.annotation.Event, Event> implements ChannelFilter<Event> {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean filter(Event event) {
        Class<? extends CustomFilter<?>> filter = annotation.filter();
        if (!filter.equals(DefualtFilter.class)) {
            try {
                CustomFilter customFilter = filter.getConstructor().newInstance();
                return customFilter.filter(event);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                NumeronLogUtil.logger.error(e.getMessage(), e);
            }
        }
        return true;
    }
}
