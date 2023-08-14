package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefaultFilter;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 此注解用于标记一个事件处理方法, 与 {@link Message} 类似
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/3/22 10:45
 * @see Message
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Deprecated
public @interface Event {
    String value() default "";

    Class<? extends CustomFilter<?>> filter() default DefaultFilter.class;

    EventPriority priority() default EventPriority.NORMAL;

    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;
}
