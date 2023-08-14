package com.erzbir.numeron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 在标记的方法执行后会调用此注解指定的方法
 * </p>
 *
 * @author Erzbir
 * @Date 2023/8/13
 * @see RunBefore
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunAfter {
    /**
     * <p>只需指定函数全限定名, 例如: {@code com.erzbir.test.Say.sayHello}</p>
     *
     * <p>目前只支持无参函数</p>
     */
    String value();

}
