package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Targets;
import com.erzbir.numeron.api.filter.Matcher;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 针对 {@link  Filter} 和 {@link com.erzbir.numeron.annotation.Filters} 注解的内容过滤
 *
 * @author Erzbir
 * @Date 2023/11/1
 * @see Filter
 * @see com.erzbir.numeron.annotation.Filters
 */
public class FilterAnnotationFilter extends AbstractAnnotationChannelFilter<Filter, Event> {
    @Override
    public boolean filter(Event event) {
        Targets targets = annotation.targets();
        TargetsAnnotationFilter targetsFilter = (TargetsAnnotationFilter) AnnotationFilterFactory.INSTANCE.create(targets);
        return filter0(event) && targetsFilter.filter(event);
    }

    private boolean filter0(Event event) {
        if (event instanceof MessageEvent messageEvent) {
            Matcher<String, String> matcher = annotation.matchType().getMatcher();
            return matcher.match(messageEvent.getMessage().contentToString(), annotation.value());
        }
        return true;
    }
}
