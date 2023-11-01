package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.api.filter.ChannelFilter;
import lombok.Getter;
import net.mamoe.mirai.event.Event;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2023/6/30 17:44
 */
@Getter
public abstract class AbstractAnnotationChannelFilter<E extends Annotation, T extends Event> implements ChannelFilter<T> {
    protected E annotation;

    public AbstractAnnotationChannelFilter<E, T> setAnnotation(E annotation) {
        this.annotation = annotation;
        return this;
    }
}