package com.erzbir.numeron.core.context;

import com.erzbir.numeron.core.proxy.MiraiEventChannelProxy;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
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

    private final Map<EventPriority, Collection<?>> listenerCollectionMap;


    private final MiraiEventChannelProxy miraiEventChannelProxy;


    public ListenerContext() {
        try {
            listenerCollectionMap = getListenerMap();
            miraiEventChannelProxy = new MiraiEventChannelProxy(new HashMap<>());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException |
                 InvocationTargetException e) {
            NumeronLogUtil.logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private Listener<? extends Event> getListener(Object listenerRegistry) throws NoSuchFieldException, IllegalAccessException {
        Field listener = listenerRegistry.getClass().getDeclaredField("listener");
        listener.setAccessible(true);
        return (Listener<? extends Event>) listener.get(listenerRegistry);
    }

    @SuppressWarnings({"unchecked"})
    private Map<EventPriority, Collection<?>> getListenerMap() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Field mapField = Class.forName("net.mamoe.mirai.internal.event.EventListeners").getDeclaredField("map");
        mapField.setAccessible(true);
        Object companion = Class.forName("net.mamoe.mirai.internal.event.EventChannelToEventDispatcherAdapter").getDeclaredField("Companion").get(null);
        Object adapterInstance = companion.getClass().getDeclaredMethod("getInstance").invoke(companion);
        Field eventListeners = Class.forName("net.mamoe.mirai.internal.event.EventChannelImpl").getDeclaredField("eventListeners");
        eventListeners.setAccessible(true);
        Object eventListenerInstance = eventListeners.get(adapterInstance);
        Object mapInstance = mapField.get(eventListenerInstance);
        return (Map<EventPriority, Collection<?>>) mapInstance;
    }

    public MiraiEventChannelProxy.EventChannelMethodInvokeInter getListenerRegister() {
        return miraiEventChannelProxy.getProxy();
    }

    private void cancel(Listener<? extends Event> listener) {
        listener.cancel(null);
    }

    public void cancelAll() {
        listenerCollectionMap.forEach((k, v) -> v.forEach(t -> {
            try {
                cancel(getListener(t));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
//        context.forEach((k, v) -> {
//            if (v.isActive()) {
//                cancel(v);
//            }
//        });
    }
}