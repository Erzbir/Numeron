package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.permission.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date 2023/11/1
 * @see com.erzbir.numeron.api.permission.PermissionType
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    PermissionType permission() default PermissionType.NORMAL;
}
