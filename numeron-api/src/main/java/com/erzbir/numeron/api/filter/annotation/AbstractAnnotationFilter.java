package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.api.filter.AnnotationFilter;
import lombok.Getter;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2023/6/30 17:44
 */
@Getter
public abstract class AbstractAnnotationFilter<E extends Annotation> implements AnnotationFilter {
    protected E annotation;

    @SuppressWarnings({"unchecked"})
    public AnnotationFilter setAnnotation(Annotation annotation) {
        this.annotation = (E) annotation;
        return this;
    }
}
