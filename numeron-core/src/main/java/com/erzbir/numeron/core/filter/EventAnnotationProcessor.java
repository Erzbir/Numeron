package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

import java.lang.reflect.AnnotatedElement;

/**
 * 有关事件的处理器接口
 *
 * @author Erzbir
 * @Date 2023/11/1
 */
public interface EventAnnotationProcessor extends AnnotationProcessor {
    boolean process(AnnotatedElement annotatedElement, Event event);
}
