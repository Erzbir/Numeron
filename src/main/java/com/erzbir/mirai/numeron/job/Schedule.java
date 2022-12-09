package com.erzbir.mirai.numeron.job;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:26
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Schedule {
    String cron() default "";

    String id();
}
