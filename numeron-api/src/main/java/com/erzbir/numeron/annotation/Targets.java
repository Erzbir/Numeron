package com.erzbir.numeron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 如果此注解中的数组长度不为空, 则根据 bot & sender & group 进行过滤, 为空则不过滤 (返回 true)
 * </p>
 *
 * @author Erzbir
 * @Date 2023/9/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Targets {
    long[] bots() default {};

    long[] senders() default {};

    long[] groups() default {};
}