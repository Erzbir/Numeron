package com.erzbir.numeron.core.context;

import com.erzbir.numeron.core.proxy.MiraiEventChannel$Proxy;
import com.erzbir.numeron.core.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/3/17 23:26
 * <p>
 * 监听器上下文
 * </p>
 */
public class ListenerContext {
    public static final ListenerContext INSTANCE = new ListenerContext();

    private final HashMap<String, Listener<? extends Event>> context = new HashMap<>();
    private final Map<EventPriority, ?> listenerCollectionMap;
    private final MiraiEventChannel$Proxy miraiEventChannel$Proxy;


    public ListenerContext() {
        try {
            listenerCollectionMap = getListenerMap();
            miraiEventChannel$Proxy = new MiraiEventChannel$Proxy(new HashMap<>());
            miraiEventChannel$Proxy.setInvokeAfter(() -> add((Listener<? extends Event>) miraiEventChannel$Proxy.getRet()));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException |
                 InvocationTargetException e) {
            NumeronLogUtil.logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void add(Listener<? extends Event> listener) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTrace[stackTrace.length - 1];
        String method = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
        add(method, listener);
    }

    @SuppressWarnings({"unchecked"})
    private Map<EventPriority, ?> getListenerMap() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Class<?> eventListenersClass = Class.forName("net.mamoe.mirai.internal.event.EventListeners");
        Field mapField = eventListenersClass.getDeclaredField("map");
        mapField.setAccessible(true);
        Class<?> aClass2 = Class.forName("net.mamoe.mirai.internal.event.EventChannelToEventDispatcherAdapter");
        Field companionField = aClass2.getDeclaredField("Companion");
        Object companion = companionField.get(null);
        Method getInstance = companion.getClass().getDeclaredMethod("getInstance");
        Object adapterInstance = getInstance.invoke(companion);
        Class<?> eventChannelImplClass = Class.forName("net.mamoe.mirai.internal.event.EventChannelImpl");
        Field eventListeners = eventChannelImplClass.getDeclaredField("eventListeners");
        eventListeners.setAccessible(true);
        Object eventListenerInstance = eventListeners.get(adapterInstance);
        Object mapInstance = mapField.get(eventListenerInstance);
        return (Map<EventPriority, ?>) mapInstance;
    }

    public void add(String method, Listener<? extends Event> listener) {
        context.put(method, listener);
    }

    public MiraiEventChannel$Proxy.EventChannelMethodInvokeInter getListenerRegister() {
        return miraiEventChannel$Proxy.getProxy();
    }

    public Listener<? extends Event> get(String method) {
        return context.get(method);
    }

    public void cancelOne(String method) {
        cancel(context.get(method));
    }

    private void cancel(Listener<? extends Event> listener) {
        listener.cancel(null);
    }

    public void cancelAll() {
        context.forEach((k, v) -> {
            if (v.isActive()) {
                cancel(v);
            }
        });
    }
}