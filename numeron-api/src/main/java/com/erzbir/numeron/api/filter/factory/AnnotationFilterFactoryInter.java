package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;

import java.lang.annotation.Annotation;

/**
 * 注解过滤器工厂接口
 *
 * @author Erzbir
 * @Date 2023/7/27
 */
@FunctionalInterface
public interface AnnotationFilterFactoryInter extends FilterFactory<Annotation> {
    Filter<? extends Event> create(Annotation annotation);
}
