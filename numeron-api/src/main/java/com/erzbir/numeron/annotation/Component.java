package com.erzbir.numeron.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 标记一个需要实例化的类
 * </p>
 * <p>
 * 如果一个注解例如 {@link  Listener} 被此注解标记, 那么 {@link Listener} 所标记的也是需要实例化的类
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/12/12 01:25
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Component {
    String value() default "";
}
