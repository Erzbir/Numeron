package com.erzbir.numeron.core.handler;

import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2023/3/22 10:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {
    EventPriority priority() default EventPriority.NORMAL;

    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;
}
