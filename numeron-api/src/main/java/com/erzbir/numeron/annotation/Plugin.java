package com.erzbir.numeron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 用于标记一个插件
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/6/30 12:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Plugin {
    String value() default "";
}
