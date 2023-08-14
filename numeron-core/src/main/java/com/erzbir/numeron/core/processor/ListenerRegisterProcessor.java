package com.erzbir.numeron.core.processor;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.annotation.AbstractAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.factory.annotation.AnnotationFilterFactory;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.register.EventHandlerRegister;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.GlobalEventChannel;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>
 * 从 bean 容器中获取有特定注解的 bean, 并根据方法上的注解过滤 channel 后注册事件监听
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * @see Processor
 */
public class ListenerRegisterProcessor implements Processor {
    public static EventChannel<? extends net.mamoe.mirai.event.Event> channel;
    private final EventHandlerRegister eventHandlerRegister = EventHandlerRegister.INSTANCE;
    private final AppContext context = AppContext.INSTANCE;

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel 未过滤的监听频道
     * @param method  要过滤的方法
     * @return EventChannel
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @NotNull
    private <E extends Annotation> EventChannel<? extends net.mamoe.mirai.event.Event> toFilter(@NotNull EventChannel<? extends net.mamoe.mirai.event.Event> channel, Method method) {
        Annotation methodAnnotation = method.getAnnotation(MessageFilter.class);
        if (methodAnnotation == null) {
            methodAnnotation = method.getAnnotation(CommonFilter.class);
        }
        if (methodAnnotation == null) {
            return channel;
        }
        AbstractAnnotationChannelFilter filter = AnnotationFilterFactory.INSTANCE.create(methodAnnotation);
        filter.setAnnotation(methodAnnotation);
        return filter.filter(channel);
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

    private void registerMethod(Object bean, Method method, EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Handler handlerAnnotation = method.getAnnotation(Handler.class);
        Scope scope = method.getAnnotation(Scope.class);
        EventChannel<? extends net.mamoe.mirai.event.Event> scopedEventChannel = eventChannel;
        if (scope != null) {
            CoroutineScope coroutineScope = getCoroutineScope(scope);
            scopedEventChannel = bindScope(channel, coroutineScope);
        }
//        SimpleAnnotationParser annotationParser = new SimpleAnnotationParser(methodAnnotation, "priority", "concurrency");
//        annotationParser.inject(method);
//        Map<String, Object> resultMap = annotationParser.getResultMap();
//        EventPriority priority = (EventPriority) resultMap.get("prior]ity");
//        ConcurrencyKind concurrency = (ConcurrencyKind) resultMap.get("concurrency");
        EventPriority priority = handlerAnnotation.priority();
        ConcurrencyKind concurrency = handlerAnnotation.concurrency();
        EventChannel<? extends net.mamoe.mirai.event.Event> filteredScopedChannel = toFilter(scopedEventChannel, method);
        eventHandlerRegister.register(bean, method, filteredScopedChannel, priority, concurrency);
    }

    /**
     * 执行消息处理方法的注册方法
     *
     * @param bean bean 对象
     */
    private void registerMethods(Object bean) {
        Class<?> beanClass = bean.getClass();
        String name = beanClass.getName();
        NumeronLogUtil.logger.info("扫瞄到 " + name);
        Scope scope = beanClass.getAnnotation(Scope.class);
        EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel;
        CoroutineScope coroutineScope;
        coroutineScope = getCoroutineScope(scope);
        eventChannel = bindScope(channel, coroutineScope);
        Arrays.stream(beanClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Handler.class))
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

    @Override
    public void onApplicationEvent() {
        ListenerRegisterProcessor.channel = GlobalEventChannel.INSTANCE;
        NumeronLogUtil.info("开始注册注解消息处理监听......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> registerMethods(v));
        NumeronLogUtil.info("注解消息处理监听注册完毕\n");
    }

    @Override
    public void destroy() {

    }
}