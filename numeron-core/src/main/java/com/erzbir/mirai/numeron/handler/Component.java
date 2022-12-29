package com.erzbir.mirai.numeron.handler;

import java.lang.annotation.*;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:25
 * <p>
 * 只有被此注解显示或隐式标注的才会被{@code}ClassScanner 扫瞄
 * </p>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Component {
    String value() default "";
}
