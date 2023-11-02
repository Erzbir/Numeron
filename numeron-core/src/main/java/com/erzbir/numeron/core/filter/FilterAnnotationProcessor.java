package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Filters;
import com.erzbir.numeron.api.filter.annotation.FilterAnnotationFilter;
import com.erzbir.numeron.api.filter.enums.MultiMatchType;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Date 2023/9/27
 */
public class FilterAnnotationProcessor implements EventAnnotationProcessor {
    public static final FilterAnnotationProcessor INSTANCE = new FilterAnnotationProcessor();

    @Override
    public boolean process(AnnotatedElement annotatedElement, Event event) {
        Filters filters = annotatedElement.getAnnotation(Filters.class);
        Filter filterAnn = annotatedElement.getAnnotation(Filter.class);
        List<Filter> filterList;
        MultiMatchType multiMatchType = MultiMatchType.ANY;
        if (filters != null) {
            filterList = List.of(filters.value());
            multiMatchType = filters.multiMatchType();
        } else {
            filterList = new ArrayList<>();
        }
        if (filterAnn != null) {
            filterList.add(filterAnn);
        }
        if (filterList.isEmpty()) {
            return true;
        }
        Stream<com.erzbir.numeron.annotation.Filter> stream = filterList.stream();
        switch (multiMatchType) {
            case ALL -> {
                return stream.allMatch(filter -> match(filter, event));
            }
            case ANY -> {
                return stream.anyMatch(filter -> match(filter, event));
            }
            case NONE -> {
                return true;
            }
            default -> throw new RuntimeException();
        }
    }

    private boolean match(com.erzbir.numeron.annotation.Filter filter, Event event) {
        FilterAnnotationFilter filterAnnotationFilter = (FilterAnnotationFilter) AnnotationFilterFactory.INSTANCE.create(filter);
        return filterAnnotationFilter.filter(event);
    }
}
