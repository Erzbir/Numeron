package com.erzbir.mirai.numeron.handler;


import com.erzbir.mirai.numeron.filter.permission.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/12/1 20:32
 * <p>用此注解标注方法用于生成此方法的命令</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {
    String name();

    String dec();

    PermissionType permission();

    String help() default "";
}
