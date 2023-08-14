package com.erzbir.numeron.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>标记一个监听类, 此标记标记的类会先实例化后放进 {@link  com.erzbir.numeron.api.context.BeanFactory}</p>
 *
 * @author Erzbir
 * @Date: 2022/11/18 14:27
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Listener {
    String value() default "";
}
