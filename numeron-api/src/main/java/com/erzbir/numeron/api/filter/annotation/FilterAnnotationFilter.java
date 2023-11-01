package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Targets;
import com.erzbir.numeron.api.filter.Matcher;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 针对 {@code Filter 和 Filters} 注解的内容过滤
 *
 * @author Erzbir
 * @Date 2023/11/1
 */
public class FilterAnnotationFilter extends AbstractAnnotationChannelFilter<Filter, Event> {
    @Override
    public boolean filter(Event event) {
        boolean match = true;
        Targets targets = annotation.targets();
        TargetsAnnotationFilter targetsFilter = (TargetsAnnotationFilter) AnnotationFilterFactory.INSTANCE.create(targets);

        if (event instanceof MessageEvent messageEvent) {
            Matcher<String, String> matcher = annotation.matchType().getMatcher();
            match = matcher.match(messageEvent.getMessage().contentToString(), annotation.value());
        }
        return match && targetsFilter.filter(event);
    }
}