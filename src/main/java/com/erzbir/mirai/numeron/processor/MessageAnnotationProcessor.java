package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.annotation.*;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
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
import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
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

    @NotNull
    private <E extends Annotation> EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> channel, E messageAnnotation) {
        Class<? extends Annotation> aClass = messageAnnotation.annotationType();
        return channel.filter(event -> {
            if (aClass != null && event instanceof MessageEvent event1) {
                boolean flag = true;
                FilterRule filterRule;
                MessageRule messageRule;
                String text;
                PermissionType permission;
                try {
                    filterRule = (FilterRule) aClass.getDeclaredMethod("filterRule").invoke(messageAnnotation);
                    messageRule = (MessageRule) aClass.getDeclaredMethod("messageRule").invoke(messageAnnotation);
                    text = (String) aClass.getDeclaredMethod("text").invoke(messageAnnotation);
                    permission = (PermissionType) aClass.getDeclaredMethod("permission").invoke(messageAnnotation);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                if (filterRule != null && !filterRule.equals(FilterRule.NONE)) {
                    if (event instanceof GroupMessageEvent event2) {
                        flag = filterRule.equals(FilterRule.BLACKLIST) ? GlobalConfig.isOn && (!GlobalConfig.blackList.contains(event1.getSender().getId()) &&
                                GlobalConfig.isOn && GlobalConfig.groupList.contains(event2.getGroup().getId())) :
                                (GlobalConfig.isOn && GlobalConfig.groupList.contains(event2.getGroup().getId()));
                    } else {
                        flag = filterRule.equals(FilterRule.BLACKLIST) ?
                                (GlobalConfig.isOn && !GlobalConfig.blackList.contains(event1.getSender().getId())) : GlobalConfig.isOn;
                    }
                }
                switch (messageRule) {
                    case EQUAL -> {
                        switch (permission) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().equals(text)
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().equals(text);
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().equals(text)
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().equals(text);
                    }
                    case BEGIN_WITH -> {
                        switch (permission) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().startsWith(text)
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().startsWith(text);
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().startsWith(text)
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().startsWith(text);
                    }
                    case END_WITH -> {
                        switch (permission) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().endsWith(text)
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().endsWith(text);
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().endsWith(text)
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().endsWith(text);
                    }
                    case REGEX -> {
                        switch (permission) {
                            case WHITE -> {
                                return flag && Pattern.compile(text).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && Pattern.compile(text).matcher(event1.getMessage().contentToString()).find();
                            }
                            case MASTER -> {
                                return flag && Pattern.compile(text).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && Pattern.compile(text).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        switch (permission) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().contains(text)
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().contains(text);
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().contains(text)
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().contains(text);
                    }
                }
            }
            return false;
        });
    }

    /**
     * @param bean    bean对象
     * @param method  反射获取到的bean对象的方法
     * @param channel 过滤的channel
     */
    private <E extends Annotation> void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, E messageAnnotation) {
        channel.subscribeAlways(MessageEvent.class, event -> {
            if (messageAnnotation instanceof GroupMessage && !(event instanceof GroupMessageEvent)) {
                return;
            }
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

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