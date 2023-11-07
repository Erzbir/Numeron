package com.erzbir.numeron.api.listener;


import java.util.ServiceLoader;

/**
 * {@link com.erzbir.numeron.annotation.Handler} 注解标注的方法通过此接口进行注册, 开放此接口用于提供手动注册的方式
 * @author Erzbir
 * @Date: 2023/5/2 02:44
 */
public class DefaultListenerRegister {
    public static final ListenerRegisterInter INSTANCE = ServiceLoader.load(ListenerRegisterInter.class).findFirst().orElseThrow();
}
