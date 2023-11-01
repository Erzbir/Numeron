package com.erzbir.numeron.core.proxy;

import com.erzbir.numeron.api.listener.ListenerRegisterInter;
import com.erzbir.numeron.core.listener.DefaultListenerRegisterImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/4/22 00:51
 */
public class ListenerRegisterProxy {
    private ListenerRegisterInter proxy;
    private Runnable functionBefore;
    private Runnable functionAfter;
    private Map<String, Object> argsMap;
    private Object ret;

    public ListenerRegisterProxy(@NotNull Map<String, Object> argsMap) {
        this(() -> {
        }, () -> {
        }, new DefaultListenerRegisterImpl(), argsMap);
    }

    public ListenerRegisterProxy() {
        this(() -> {
        }, () -> {
        }, new DefaultListenerRegisterImpl(), new HashMap<>());
    }

    public ListenerRegisterProxy(Runnable functionBefore, Runnable functionAfter, ListenerRegisterInter proxyTarget, @NotNull Map<String, Object> argsMap) {
        Class<? extends ListenerRegisterInter> aClass = proxyTarget.getClass();
        this.proxy = (ListenerRegisterInter) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationImpl(proxyTarget));
        this.functionAfter = functionAfter;
        this.functionBefore = functionBefore;
        this.argsMap = argsMap;
    }

    public Object getArgv(String name) {
        return argsMap.get(name);
    }

    public void setArgsMap(Map<String, Object> argsMap) {
        this.argsMap = argsMap;
    }

    public Object getRet() {
        return ret;
    }

    public ListenerRegisterInter getProxy() {
        return proxy;
    }

    public void setInvokeBefore(Runnable function) {
        functionBefore = function;
    }

    public void setInvokeAfter(Runnable function) {
        functionAfter = function;
    }

    class AnoListenerRegister extends DefaultListenerRegisterImpl {

    }

    private class InvocationImpl implements InvocationHandler {
        private final Object target;

        public InvocationImpl(Object target) {
            this.target = target;
        }

        public Object getTarget() {
            return target;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            functionBefore.run();
            ret = method.invoke(target, args);
            functionAfter.run();
            return ret;
        }
    }
}
