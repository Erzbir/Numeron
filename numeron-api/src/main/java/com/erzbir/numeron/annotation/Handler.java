package com.erzbir.numeron.annotation;

import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 将一个存在 {@link Listener} 注解的类的一个方法标记为一个监听事件的回调函数
 * </p>
 *
 * @author Erzbir
 * @Date 2023/8/13
 * @see net.mamoe.mirai.event.EventHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Handler {
    String value() default "";

    /**
     * @return 监听事件的优先级
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * @return 协程并发类型
     */
    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;
}
