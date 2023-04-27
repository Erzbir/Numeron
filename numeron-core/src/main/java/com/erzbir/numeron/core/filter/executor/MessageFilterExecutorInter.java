package com.erzbir.numeron.core.filter.executor;

import net.mamoe.mirai.event.Event;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:07
 */
public interface MessageFilterExecutorInter {
    Boolean filter(Event event, Annotation annotation);
}
