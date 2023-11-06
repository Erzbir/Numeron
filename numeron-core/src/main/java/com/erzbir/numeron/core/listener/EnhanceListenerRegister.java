package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.api.listener.ListenerRegisterInter;
import com.erzbir.numeron.core.filter.CommonAnnotationProcessor;
import com.erzbir.numeron.core.filter.FilterAnnotationProcessor;
import com.erzbir.numeron.core.filter.PermissionAnnotationProcessor;
import com.erzbir.numeron.core.filter.TargetsAnnotationProcessor;
import com.erzbir.numeron.core.register.DefaultMethodRegister;
import com.erzbir.numeron.core.register.MethodRegister;
import com.erzbir.numeron.core.util.ListenerAnnotationUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.event.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Erzbir
 * @Date 2023/9/25
 */
@Setter
@Getter
public class EnhanceListenerRegister implements ListenerRegisterInter {
    private MethodRegister methodRegister = new DefaultMethodRegister();
    private DefaultListenerRegister defaultListenerRegister = new DefaultListenerRegister();

    @Override
    public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext) {
        registerListenerHost(channel, (Object) listenerHost, coroutineContext);
    }

    @Override
    public void registerListenerHost(EventChannel<? extends Event> channel, Object bean, CoroutineContext coroutineContext) {
        if (!bean.getClass().isAnnotationPresent(com.erzbir.numeron.annotation.Listener.class)) {
            return;
        }
        Class<?> beanClass = bean.getClass();
        Method[] methods = ListenerAnnotationUtil.getCallBackMethods(bean);
        if (methods == null) {
            return;
        }
        String name = beanClass.getName();
        NumeronLogUtil.logger.info("扫瞄到 " + name);
        Arrays.stream(methods)
                .forEach(method -> {
                    String s = Arrays.toString(method.getParameterTypes())
                            .replaceFirst("\\[", "(")
                            .replace("]", ")");
                    methodRegister.registerMethod(bean, method, toFilter(channel, method), coroutineContext);
                    NumeronLogUtil.logger.info(method.getName() + s + " 处理方法注册完毕");
                });
    }

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel 未过滤的监听频道
     * @param method  要过滤的方法
     * @return EventChannel
     */
    @NotNull
    private EventChannel<? extends net.mamoe.mirai.event.Event> toFilter(@NotNull EventChannel<? extends net.mamoe.mirai.event.Event> channel, Method method) {
        return channel.filter(event -> FilterAnnotationProcessor.INSTANCE.process(method, event)
                && CommonAnnotationProcessor.INSTANCE.process(method, event)
                && PermissionAnnotationProcessor.INSTANCE.process(method, event)
                && TargetsAnnotationProcessor.INSTANCE.process(method, event));
    }

    @Override
    public <E extends Event> Listener<E> subscribe(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Function<E, ListeningStatus> handler) {
        return defaultListenerRegister.subscribe(channel, eventClass, coroutineContext, concurrency, priority, handler);
    }

    @Override
    public <E extends Event> Listener<E> subscribeOnce(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return defaultListenerRegister.subscribeOnce(channel, eventClass, coroutineContext, concurrency, priority, handler);

    }

    @Override
    public <E extends Event> Listener<E> subscribeAlways(EventChannel<? extends Event> channel, Class<? extends E> eventClass, CoroutineContext coroutineContext, ConcurrencyKind concurrency, EventPriority priority, Consumer<E> handler) {
        return defaultListenerRegister.subscribeAlways(channel, eventClass, coroutineContext, concurrency, priority, handler);

    }
}
