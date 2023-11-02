package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.enums.MultiMatchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 指定过滤, {@link Filter} 的负数形式
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
     * 如果是 {@code ANY}, 那么遍历到一个  {@link Filter} 为 {@code true} 时的过滤生效 或者 遍历完没有为 {@code true} 的则不生效
     * <p><p/>
     * 如果是 {@code ALL}, 那么会遍历完整个 {@link Filter} 数组, 全为 {@code true} 时过滤生效
     */
    MultiMatchType multiMatchType() default MultiMatchType.ANY;
}
