package com.erzbir.mirai.numeron.listener;


import com.erzbir.mirai.numeron.handler.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Erzbir
 * @Date: 2022/11/18 14:27
 * <p>一个标记, 用于获取bean, 此标记标记的类会先实例化后放进{@code AppContext}</p>
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Listener {
}
