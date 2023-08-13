package com.erzbir.numeron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 此注解标记的类上必须要有 {@link Component} 注解以及包含 {@link  Component} 注解的注解标注
 * </p>
 *
 * <p>
 * 此换注解标注的类在启动时不会被实例化, 在获取此类实例时才会实例化
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/6/14 13:48
 * @see Component
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Lazy {
    boolean value() default true;
}
