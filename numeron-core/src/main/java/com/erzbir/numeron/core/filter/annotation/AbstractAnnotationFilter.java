package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2023/6/30 17:44
 */
public abstract class AbstractAnnotationFilter<E extends Annotation, T extends Event> implements Filter<T> {
    protected E annotation;

    public abstract EventChannel<? extends T> filter(EventChannel<? extends T> channel);

    public AbstractAnnotationFilter<E, T> setAnnotation(E annotation) {
        this.annotation = annotation;
        return this;
    }

    public E getAnnotation() {
        return annotation;
    }
}
