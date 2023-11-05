package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.annotation.CommonFilter;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Erzbir
 * @Date 2023/11/2
 */
public class CommonAnnotationProcessor implements EventAnnotationProcessor {
    public static final CommonAnnotationProcessor INSTANCE = new CommonAnnotationProcessor();

    @Override
    public boolean process(AnnotatedElement annotatedElement, Event event) {
        CommonFilter commonFilter = annotatedElement.getAnnotation(CommonFilter.class);
        if (commonFilter == null) {
            return true;
        }
        return AnnotationFilterFactory.INSTANCE.create(commonFilter).filter(event);
    }
}
