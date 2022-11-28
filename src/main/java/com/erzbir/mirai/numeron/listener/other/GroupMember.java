package com.erzbir.mirai.numeron.listener.other;

import com.erzbir.mirai.numeron.enums.MessageRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/27 10:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GroupMember {
    MessageRule messageRule() default MessageRule.EQUAL;
}
