package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.AnnotationFilter;
import com.erzbir.numeron.api.filter.annotation.CommonAnnotationFilter;
import com.erzbir.numeron.api.filter.annotation.FilterAnnotationFilter;
import com.erzbir.numeron.api.filter.annotation.PermissionAnnotationFilter;
import com.erzbir.numeron.api.filter.annotation.TargetsAnnotationFilter;
import com.erzbir.numeron.exception.FilterNotFoundException;

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
    public AnnotationFilter create(Annotation annotation) {
        if (CommonFilter.class.equals(annotation.annotationType())) {
            return new CommonAnnotationFilter().setAnnotation(annotation);
        } else if (Targets.class.equals(annotation.annotationType())) {
            return new TargetsAnnotationFilter().setAnnotation(annotation);
        } else if (Filters.class.equals(annotation.annotationType())) {
            return new FilterAnnotationFilter().setAnnotation(annotation);
        } else if (Filter.class.equals(annotation.annotationType())) {
            return new FilterAnnotationFilter().setAnnotation(annotation);
        } else if (Permission.class.equals(annotation.annotationType())) {
            return new PermissionAnnotationFilter().setAnnotation(annotation);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
