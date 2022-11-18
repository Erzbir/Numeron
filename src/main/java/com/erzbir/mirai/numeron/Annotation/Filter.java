package com.erzbir.mirai.numeron.Annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:16
 */
@Component
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Filter {
}
