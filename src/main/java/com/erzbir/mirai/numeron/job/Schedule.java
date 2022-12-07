package com.erzbir.mirai.numeron.job;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:26
 */
@Inherited
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Schedule {
    String cron() default "";

    String id();
}
