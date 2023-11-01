package com.erzbir.numeron.annotation;


import com.erzbir.numeron.api.permission.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>用此注解标注方法用于生成此方法的命令</p>
 *
 * @author Erzbir
 * @Date: 2022/12/1 20:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {
    /**
     * @return 指令组名
     */
    String name();

    /**
     * @return 指令描述
     */
    String dec();

    /**
     * @return 指令权限
     */
    PermissionType permission() default PermissionType.EVERYONE;

    /**
     * @return 指令示例或帮助
     */
    String help() default "";
}
