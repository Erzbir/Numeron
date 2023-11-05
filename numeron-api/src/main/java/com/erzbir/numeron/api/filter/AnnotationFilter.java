package com.erzbir.numeron.api.filter;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public interface AnnotationFilter extends Filter {
    AnnotationFilter setAnnotation(Annotation annotation);
}
