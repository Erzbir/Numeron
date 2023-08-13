package com.erzbir.numeron.api.filter.factory.annotation;

import com.erzbir.numeron.annotation.CommonFilter;
import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.exception.FilterNotFoundException;
import com.erzbir.numeron.api.filter.annotation.AbstractAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.annotation.CommonAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.annotation.MessageAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactoryInter;
import net.mamoe.mirai.event.Event;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date 2023/8/13
 */
public class AnnotationFilterFactory implements AnnotationFilterFactoryInter {
    public static final AnnotationFilterFactory INSTANCE = new AnnotationFilterFactory();

    private AnnotationFilterFactory() {

    }

    @Override
    public AbstractAnnotationChannelFilter<? extends Annotation, ? extends Event> create(Annotation annotation) {
        if (MessageFilter.class.equals(annotation.annotationType())) {
            return new MessageAnnotationChannelFilter();
        } else if (CommonFilter.class.equals(annotation.annotationType())) {
            return new CommonAnnotationChannelFilter();
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
