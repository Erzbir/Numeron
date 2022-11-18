package com.erzbir.mirai.numeron.Annotation;

import com.erzbir.mirai.numeron.enums.MessageRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/18 14:34
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface UserMessage {
    boolean filter() default false;

    MessageRule messageRule() default MessageRule.EQUAL;

    String text() default "";
}
