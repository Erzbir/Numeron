package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.annotation.*;
import com.erzbir.numeron.exception.FilterNotFoundException;
import net.mamoe.mirai.event.Event;

import java.lang.annotation.Annotation;

/**
 * 注解过滤器工厂
 *
 * @author Erzbir
 * @Date 2023/8/13
 */
public class AnnotationFilterFactory implements AnnotationFilterFactoryInter {
    public static final AnnotationFilterFactory INSTANCE = new AnnotationFilterFactory();

    private AnnotationFilterFactory() {

    }

    @Override
    public AbstractAnnotationChannelFilter<? extends Annotation, ? extends Event> create(Annotation annotation) {
        if (CommonFilter.class.equals(annotation.annotationType())) {
            return new CommonAnnotationFilter().setAnnotation((CommonFilter) annotation);
        } else if (Targets.class.equals(annotation.annotationType())) {
            return new TargetsAnnotationFilter().setAnnotation((Targets) annotation);
        } else if (Filters.class.equals(annotation.annotationType())) {
            return new FilterAnnotationFilter().setAnnotation((Filter) annotation);
        } else if (Filter.class.equals(annotation.annotationType())) {
            return new FilterAnnotationFilter().setAnnotation((Filter) annotation);
        } else if (Permission.class.equals(annotation.annotationType())) {
            return new PermissionAnnotationFilter().setAnnotation((Permission) annotation);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
