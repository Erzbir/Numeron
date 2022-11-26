package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.filter.ChannelFilterInter;
import com.erzbir.mirai.numeron.filter.message.MessageFilterFactory;
import com.erzbir.mirai.numeron.filter.permission.PermissionFilterFactory;
import com.erzbir.mirai.numeron.filter.rule.RuleFilterFactory;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * <p>
 * 此类为消息处理类, 从bean容器中获取有特定注解的bean, 并根据方法上的注解执行 过滤channel/执行对应方法等
 * </p>
 */
@Processor
@Slf4j
public class MessageAnnotationProcessor implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    public static ApplicationContext context;
    public static EventChannel<BotEvent> channel;
    public static Bot bot;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        MessageAnnotationProcessor.context = context;
    }

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel           未过滤的监听频道
     * @param messageAnnotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     * @return EventChannel
     */
    @NotNull
    private <E extends Annotation> EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> channel, E messageAnnotation) {
        Class<? extends Annotation> aClass = messageAnnotation.annotationType(); // 这里获取注解的字节码
        return channel.filter(event -> {
            if (aClass != null && event instanceof MessageEvent event1) {
                boolean flag = GlobalConfig.isOn;
                FilterRule filterRule;
                MessageRule messageRule;
                String text;
                PermissionType permission;
                try {
                    // 以下操作通过反射调用注解的方法, 再强制类型转换成对应的类型
                    filterRule = (FilterRule) aClass.getDeclaredMethod("filterRule").invoke(messageAnnotation);
                    messageRule = (MessageRule) aClass.getDeclaredMethod("messageRule").invoke(messageAnnotation);
                    text = (String) aClass.getDeclaredMethod("text").invoke(messageAnnotation);
                    permission = (PermissionType) aClass.getDeclaredMethod("permission").invoke(messageAnnotation);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                ChannelFilterInter channelFilter1 = RuleFilterFactory.INSTANCE.create(filterRule);
                ChannelFilterInter channelFilter2 = MessageFilterFactory.INSTANCE.create(messageRule);
                ChannelFilterInter channelFilter3 = PermissionFilterFactory.INSTANCE.create(permission);
                if (event1 instanceof GroupMessageEvent event2) {
                    return flag && channelFilter1.filter(event2, text)
                            && channelFilter2.filter(event2, text)
                            && channelFilter3.filter(event2, text);
                } else {
                    return flag && channelFilter2.filter(event1, text)
                            && channelFilter3.filter(event1, text);
                }
            }
            return false;
        });
    }

    /**
     * @param bean              bean对象
     * @param method            反射获取到的bean对象的方法
     * @param channel           过滤的channel
     * @param messageAnnotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     */
    private <E extends Annotation> void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, E messageAnnotation) {
        new Thread(() -> channel.subscribeAlways(MessageEvent.class, event -> {
            /*
             * 这里判断的原因是: 所有事件都在这一个方法实现, 但是如果method是@GroupMessage注解标记的方法而event却是一个UserMessageEvent,
             * 这时就会出现异常, 因为GroupMessageEvent和UserMessageEvent都是MessageEvent的子类,
             * 这个if也可以不要就让他抛出异常, 因为我们期望的是@GroupMessage标记的就处理群消息而不是私聊消息
             */
            if ((messageAnnotation instanceof GroupMessage && !(event instanceof GroupMessageEvent))
                    || (messageAnnotation instanceof UserMessage && !(event instanceof UserMessageEvent))) {
                return;
            }
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        })).start();
    }

    /**
     * 这个方法是spring自动调用的, 用来扫瞄有规定注解的方法
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        bot = context.getBean(Bot.class);
        channel = bot.getEventChannel();
        log.info("开始注册消息处理......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> {
            Object bean = context.getBean(k);
            log.info("扫瞄到 " + k);
            List.of(bean.getClass().getDeclaredMethods()).forEach(method -> {
                GroupMessage groupMessage = method.getDeclaredAnnotation(GroupMessage.class);
                UserMessage userMessage = method.getDeclaredAnnotation(UserMessage.class);
                Message message = method.getDeclaredAnnotation(Message.class);
                if (groupMessage != null) {
                    execute(bean, method, toFilter(channel, groupMessage), groupMessage);
                    return;
                }
                if (userMessage != null) {
                    execute(bean, method, toFilter(channel, userMessage), userMessage);
                    return;
                }
                if (message != null) {
                    execute(bean, method, toFilter(channel, message), message);
                }
            });
        });
        log.info("消息处理注册完成");
    }
}