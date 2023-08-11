package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.TargetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2023/6/26 23:24
 * <p>
 * 此注解要生效必须让 {@link Message} 或者 {@link Event} 生效
 * </p>
 *
 * <p>
 * 用于指定监听目标
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ListenTarget {
    /**
     * @return 监听类型
     */
    TargetType value();

    /**
     * @return 监听对象的 id (qq 号)
     */
    long id() default 0;
}
