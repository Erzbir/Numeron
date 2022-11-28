package com.erzbir.mirai.numeron.listener.other;

import com.erzbir.mirai.numeron.enums.MessageRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/27 10:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserAction {
    MessageRule messageRule() default MessageRule.EQUAL;
}
