package com.erzbir.numeron.core.processor;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.annotation.EventAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.annotation.MessageAnnotationChannelFilter;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.parser.SimpleAnnotationParser;
import com.erzbir.numeron.core.register.EventHandlerRegister;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * <p>
 * 从 bean 容器中获取有特定注解的 bean, 并根据方法上的注解 过滤 channel 和注册时间监听
 * </p>
 * @see Processor
 */
public class ListenerRegisterProcessor implements Processor {
    public static EventChannel<? extends net.mamoe.mirai.event.Event> channel;
    public static Map<String, Method> methodMap = new HashMap<>(4);

    static {
        Method[] methods = Message.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> methodMap.put(method.getName().replaceFirst("\\(.*\\)", ""), method));
    }

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel    未过滤的监听频道
     * @param annotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     * @return EventChannel
     */
    @NotNull
    private <E extends Annotation> EventChannel<? extends net.mamoe.mirai.event.Event> toFilter(@NotNull EventChannel<? extends net.mamoe.mirai.event.Event> channel, E annotation) {
        if (annotation instanceof Message) {
            return new MessageAnnotationChannelFilter().setAnnotation((Message) annotation).filter((EventChannel<? extends MessageEvent>) channel);
        } else if (annotation instanceof Event) {
            return new EventAnnotationChannelFilter().setAnnotation((Event) annotation).filter(channel);
        }
        return channel;
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

    /**
     * @param annotation 注解
     * @return 是否是需要的注解
     */
    private boolean isNeededAnnotation(Annotation annotation) {
        return annotation instanceof Message || annotation instanceof Event || annotation instanceof Scope;
    }

    private void registerMethod(Object bean, Method method, EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel, Annotation annotation) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SimpleAnnotationParser annotationParser = new SimpleAnnotationParser(annotation, "priority", "concurrency");
        annotationParser.inject(method);
        Map<String, Object> resultMap = annotationParser.getResultMap();
        EventPriority priority = (EventPriority) resultMap.get("priority");
        ConcurrencyKind concurrency = (ConcurrencyKind) resultMap.get("concurrency");

        EventChannel<? extends net.mamoe.mirai.event.Event> filterChannel = toFilter(eventChannel, annotation);
        EventHandlerRegister.INSTANCE.register(bean, method, filterChannel, priority, concurrency);
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
        for (Method method : beanClass.getDeclaredMethods()) {
            Arrays.stream(method.getAnnotations())
                    .filter(this::isNeededAnnotation).forEach(annotation -> {
                        String s = Arrays.toString(method.getParameterTypes())
                                .replaceAll("\\[", "(")
                                .replaceAll("]", ")");
                        try {
                            registerMethod(bean, method, eventChannel, annotation);
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                            NumeronLogUtil.logger.error("ERROR", e);
                            throw new RuntimeException(e);
                        }
                        NumeronLogUtil.logger.info(method.getName() + s + " 处理方法注册完毕");
                    });
        }
    }

    @Override
    public void onApplicationEvent() {
        AppContext context = AppContext.INSTANCE;
        ListenerRegisterProcessor.channel = GlobalEventChannel.INSTANCE;
        NumeronLogUtil.info("开始注册注解消息处理监听......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> registerMethods(v));
        NumeronLogUtil.info("注解消息处理监听注册完毕\n");
    }

    @Override
    public void destroy() {

    }
}