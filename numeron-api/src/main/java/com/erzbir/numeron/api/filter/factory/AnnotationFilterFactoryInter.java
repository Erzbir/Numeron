package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.AnnotationFilter;
import com.erzbir.numeron.api.filter.Filter;

import java.lang.annotation.Annotation;

/**
 * 注解过滤器工厂接口
 *
 * @author Erzbir
 * @Date 2023/7/27
 */
@FunctionalInterface
public interface AnnotationFilterFactoryInter extends FilterFactory {
    <E extends Annotation> AnnotationFilter create(E annotation);

    @Override
    default <E> Filter create(E e) {
        return create((Annotation) e);
    }
}
