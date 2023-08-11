package com.erzbir.numeron.annotation;

import kotlinx.coroutines.CoroutineScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2023/6/27 15:17
 * <p>
 * 用于指定监听的协程作用域
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Scope {
    Class<? extends CoroutineScope> value() default DefaultScope.class;

    String name() default "";
}