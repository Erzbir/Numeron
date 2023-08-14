package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefaultFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 指定过滤
 * </p>
 *
 * <p>
 * 与 {@link MessageFilter} 不同的是, 此注解指定一个自己定义的过滤器
 * </p>
 *
 * @author Erzbir
 * @Date 2023/8/13
 * @see MessageFilter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommonFilter {
    String value() default "";

    /**
     * <p>
     * 如果 filterRule 为 CUSTOM 且值不为 {@link DefaultFilter}, 会调用自定义的过滤器
     * </p>
     *
     * @return 指定过滤器的字节码
     */
    Class<? extends CustomFilter<?>> filter() default DefaultFilter.class;
}
