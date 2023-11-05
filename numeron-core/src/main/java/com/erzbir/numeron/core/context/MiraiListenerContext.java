package com.erzbir.numeron.core.context;

import com.erzbir.numeron.api.context.ListenerContext;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/3/17 23:26
 * <p>
 * 监听器上下文
 * </p>
 */
@SuppressWarnings("all")
public class MiraiListenerContext implements ListenerContext {
    public static final MiraiListenerContext INSTANCE = new MiraiListenerContext();

    /**
     * 所有监听将会注册到这个容器中
     */
    private final EnumMap<EventPriority, Collection<net.mamoe.mirai.internal.event.ListenerRegistry>> internalListenerMap;

    private final Map<String, Listener<?>> listenerMap = new HashMap<>();


    public MiraiListenerContext() {
        try {
            internalListenerMap = getListenerMap();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException |
                 InvocationTargetException e) {
            NumeronLogUtil.logger.error("ERROR", e);
            throw new RuntimeException(e);
        }
    }


    private Listener<? extends Event> getListener(Object listenerRegistry) throws NoSuchFieldException, IllegalAccessException {
        Field listener = listenerRegistry.getClass().getDeclaredField("listener");
        listener.setAccessible(true);
        return (Listener<? extends Event>) listener.get(listenerRegistry);
    }

    @SuppressWarnings({"unchecked"})
    private EnumMap<EventPriority, Collection<net.mamoe.mirai.internal.event.ListenerRegistry>> getListenerMap() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Field mapField = Class.forName("net.mamoe.mirai.internal.event.EventListeners").getDeclaredField("map");
        mapField.setAccessible(true);
        Object companion = Class.forName("net.mamoe.mirai.internal.event.EventChannelToEventDispatcherAdapter").getDeclaredField("Companion").get(null);
        Object adapterInstance = companion.getClass().getDeclaredMethod("getInstance").invoke(companion);
        Field eventListeners = Class.forName("net.mamoe.mirai.internal.event.EventChannelImpl").getDeclaredField("eventListeners");
        eventListeners.setAccessible(true);
        Object eventListenerInstance = eventListeners.get(adapterInstance);
        Object mapInstance = mapField.get(eventListenerInstance);
        return (EnumMap<EventPriority, Collection<net.mamoe.mirai.internal.event.ListenerRegistry>>) mapInstance;
    }

    private void cancel(Listener<? extends Event> listener) {
        if (listener.isCancelled()) {
            listener = null;
            return;
        }
        listener.cancel(null);
        listener = null;
    }

    public void cancelAll() {
        internalListenerMap.forEach((k, v) -> {
//            v.forEach(t -> {
//                Listener<?> listener = t.getListener();
//                cancel(listener);
//            });
            v.clear();
        });
        System.err.println(internalListenerMap);
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public void register(Listener<?> listener) {

    }

    @Override
    public Listener<?> removeListener(String name) {
        return null;
    }

    @Override
    public Listener<?> getListener(String name) {
        return null;
    }

    @Override
    public void cancel(String name) {

    }
}