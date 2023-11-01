package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.annotation.DefaultScope;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Scope;
import com.erzbir.numeron.api.listener.ListenerHostRegister;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.filter.CommonAnnotationProcessor;
import com.erzbir.numeron.core.filter.FilterAnnotationProcessor;
import com.erzbir.numeron.core.filter.PermissionAnnotationProcessor;
import com.erzbir.numeron.core.filter.TargetsAnnotationProcessor;
import com.erzbir.numeron.core.util.AnnotationParser;
import com.erzbir.numeron.core.util.ListenerAnnotationUtil;
import com.erzbir.numeron.core.util.SimpleAnnotationParser;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.*;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Erzbir
 * @Date 2023/9/25
 */
public class EnhanceListenerHostRegister implements ListenerHostRegister {
    private final EventHandlerRegister eventHandlerRegister = EventHandlerRegister.INSTANCE;

    @Override
    public void registerListenerHost(EventChannel<? extends Event> channel, ListenerHost listenerHost, CoroutineContext coroutineContext) {
        registerMethods(listenerHost, channel);
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
        return channel.filter(event -> {
            boolean filter = FilterAnnotationProcessor.INSTANCE.process(method, event);
            filter &= TargetsAnnotationProcessor.INSTANCE.process(method, event);
            filter &= PermissionAnnotationProcessor.INSTANCE.process(method, event);
            filter &= CommonAnnotationProcessor.INSTANCE.process(method, event);
            return filter;
        });
    }

    private EventChannel<? extends net.mamoe.mirai.event.Event> bindScope(EventChannel<? extends net.mamoe.mirai.event.Event> channel, CoroutineScope scope) {
        return channel.parentScope(scope);
    }

    private CoroutineScope getCoroutineScope(Scope scope) {
        CoroutineScope coroutineScope = DefaultScope.INSTANCE;
        if (scope != null) {
            Class<? extends CoroutineScope> value = scope.value();
            String name = scope.name();
            try {
                if (!value.equals(DefaultScope.class)) {
                    Constructor<? extends CoroutineScope> constructor = value.getConstructor();
                    constructor.setAccessible(true);
                    coroutineScope = constructor.newInstance();
                    AppContext.INSTANCE.addBean("defaultScope", coroutineScope);
                }
                if (!name.isEmpty()) {
                    AppContext.INSTANCE.addBean(name, coroutineScope);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        }
        return coroutineScope;
    }

    public void registerMethod(Object bean, Method method, EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Annotation handlerAnnotation = method.getAnnotation(Handler.class);
        if (handlerAnnotation == null) {
            handlerAnnotation = method.getAnnotation(EventHandler.class);
        }
        AnnotationParser annotationParser = new SimpleAnnotationParser(handlerAnnotation, "priority", "concurrency");
        annotationParser.inject(method);
        Scope scope = method.getAnnotation(Scope.class);
        EventChannel<? extends net.mamoe.mirai.event.Event> scopedEventChannel = eventChannel;
        if (scope != null) {
            CoroutineScope coroutineScope = getCoroutineScope(scope);
            scopedEventChannel = bindScope(eventChannel, coroutineScope);
        }
        EventPriority priority = (EventPriority) annotationParser.get("priority");
        ConcurrencyKind concurrency = (ConcurrencyKind) annotationParser.get("concurrency");
        EventChannel<? extends net.mamoe.mirai.event.Event> filteredScopedChannel = toFilter(scopedEventChannel, method);
        eventHandlerRegister.register(bean, method, filteredScopedChannel, priority, concurrency);
    }

    /**
     * 执行消息处理方法的注册方法
     *
     * @param bean bean 对象
     */
    public void registerMethods(Object bean, EventChannel<? extends Event> channel) {
        Class<?> beanClass = bean.getClass();
        Method[] methods = ListenerAnnotationUtil.getCallBackMethods(bean);
        if (methods == null) {
            return;
        }
        String name = beanClass.getName();
        NumeronLogUtil.logger.info("扫瞄到 " + name);
        Scope scope = beanClass.getAnnotation(Scope.class);
        EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel;
        CoroutineScope coroutineScope;
        coroutineScope = getCoroutineScope(scope);
        eventChannel = bindScope(channel, coroutineScope);
        Arrays.stream(methods)
                .forEach(method -> {
                    String s = Arrays.toString(method.getParameterTypes())
                            .replaceFirst("\\[", "(")
                            .replace("]", ")");
                    try {
                        registerMethod(bean, method, eventChannel);
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                        NumeronLogUtil.logger.error("ERROR", e);
                        throw new RuntimeException(e);
                    }
                    NumeronLogUtil.logger.info(method.getName() + s + " 处理方法注册完毕");
                });
    }
}
