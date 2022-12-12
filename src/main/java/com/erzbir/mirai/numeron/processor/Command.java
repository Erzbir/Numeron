package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.boot.classloder.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/12/1 20:32
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {
    String name();

    String dec();

    String help();
}
