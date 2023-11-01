package com.erzbir.numeron.annotation;

import com.erzbir.numeron.enums.MatchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在 {@link Handler} 注解生效的方法上, 指定过滤规则
 *
 * @author Erzbir
 * @Date 2023/9/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Filter {
    /**
     * 要匹配的文本
     */
    String value() default "";

    /**
     * 文本匹配类型
     */
    MatchType matchType() default MatchType.TEXT_EQUALS;

    /**
     * 触发匹配的目标
     */
    Targets targets() default @Targets;

    /**
     * 权限
     */
    Permission permission() default @Permission;
}
