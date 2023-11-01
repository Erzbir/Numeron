package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.enums.MultiMatchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 指定过滤, {@code Filter} 的负数形式
 * </p>
 *
 * @author Erzbir
 * @Date 2023/9/27
 * @see Filter
 * @see MultiMatchType
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Filters {
    Filter[] value();

    /**
     * 如果是 ANY, 那么遍历到一个  {@code Filter} 为 true 时的过滤生效 或者 遍历完也没有一个生效
     * <p><p/>
     * 如果是 ALL, 那么会遍历完整个 {@code Filter} 数组
     */
    MultiMatchType multiMatchType() default MultiMatchType.ANY;
}
