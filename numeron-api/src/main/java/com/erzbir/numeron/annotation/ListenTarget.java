package com.erzbir.numeron.annotation;

import com.erzbir.numeron.enums.TargetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标注在 {@link Message} 或 {@link Event} 或 {@link Handler} 生效的方法上
 * </p>
 *
 * <p>
 * 用于指定监听目标
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/6/26 23:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
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
