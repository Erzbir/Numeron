package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.MessageFilterExecutor;
import com.erzbir.mirai.numeron.handler.factory.ExecutorFactory;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import com.erzbir.mirai.numeron.utils.MiraiLogUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * <p>
 * 此类为消息处理类, 从bean容器中获取有特定注解的bean, 并根据方法上的注解执行 过滤channel/执行对应方法等
 * </p>
 */
@SuppressWarnings("unused")
public class MessageAnnotationProcessor implements Processor {
    public static EventChannel<BotEvent> channel;
    public static Bot bot;

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel    未过滤的监听频道
     * @param annotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     * @return EventChannel
     */
    @NotNull
    private <E extends Annotation> EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> channel, E annotation) {
        return channel
                .filter(event -> MessageFilterExecutor.INSTANCE.filter(event, annotation));
    }

    /**
     * @param bean       bean对象
     * @param method     反射获取到的bean对象的方法
     * @param channel    过滤的channel
     * @param annotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     */
    private <E extends Annotation> void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, E annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ExecutorFactory.INSTANCE.create(annotation)
                .getExecute()
                .execute(method,
                        bean,
                        channel);

    }

    /**
     * @param annotation 注解
     * @return 是否是需要的注解
     */
    private boolean isNeededAnnotation(Annotation annotation) {
        return annotation instanceof GroupMessage
                || annotation instanceof UserMessage
                || annotation instanceof Message;
    }

    /**
     * 执行消息处理方法的注册方法
     *
     * @param v bean对象
     */
    private void registerMethods(Object v) {
        String name = v.getClass().getName();
        MiraiLogUtil.debug("扫瞄到 " + name);
        for (Method method : v.getClass().getDeclaredMethods()) {
            Arrays.stream(method.getAnnotations())
                    .filter(this::isNeededAnnotation).forEach(annotation -> {
                        String s = Arrays.toString(method.getParameterTypes())
                                .replaceAll("\\[", "(")
                                .replaceAll("]", ")");
                        method.setAccessible(true);
                        try {
                            execute(v, method, toFilter(channel, annotation), annotation);
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        MiraiLogUtil.info(method.getName() + s + " 处理方法注册完毕");
                    });
        }
    }

    @Override
    public void onApplicationEvent() {
        AppContext context = AppContext.INSTANT;
        bot = NumeronBot.INSTANCE.getBot();
        channel = bot.getEventChannel();
        MiraiLogUtil.verbose("开始注册注解消息处理监听......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> registerMethods(v));
        MiraiLogUtil.verbose("注解消息处理监听注册完毕\n");
    }
}