package com.erzbir.mirai.numeron.annotation.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/20 18:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataValue {
    String sql() default ""; // 此方法暂时没用, 后期打算通过反射执行sql语句
}
