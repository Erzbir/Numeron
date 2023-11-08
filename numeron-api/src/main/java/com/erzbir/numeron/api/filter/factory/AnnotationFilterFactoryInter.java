package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.AnnotationFilter;

import java.lang.annotation.Annotation;

/**
 * 注解过滤器工厂接口
 *
 * @author Erzbir
 * @Date 2023/7/27
 */
@FunctionalInterface
public interface AnnotationFilterFactoryInter extends FilterFactory<Annotation> {
    AnnotationFilter create(Annotation annotation);
}
