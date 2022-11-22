package com.erzbir.mirai.numeron.annotation.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/22 18:05
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Loader {
}
