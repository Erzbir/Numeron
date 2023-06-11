package com.erzbir.numeron.api.listener;


import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/5/2 02:44
 */
public class DefaultListenerRegister {
    public static final ListenerRegisterInter INSTANCE = ServiceLoader.load(ListenerRegisterInter.class).findFirst().orElseThrow();
}
