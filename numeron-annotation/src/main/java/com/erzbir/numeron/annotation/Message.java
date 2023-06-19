package com.erzbir.numeron.annotation;


import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/18 14:32
 * <p>标记所有消息事件</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Message {
    String value() default "";

    FilterRule filterRule() default FilterRule.NONE;

    MessageRule messageRule() default MessageRule.EQUAL;

    PermissionType permission();

    String text();

    EventPriority priority() default EventPriority.NORMAL;

    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;

    int id() default 0;
}
