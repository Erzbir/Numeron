package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.annotation.GroupMessage;
import com.erzbir.mirai.numeron.annotation.Message;
import com.erzbir.mirai.numeron.annotation.UserMessage;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
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
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 */
@Component
public class MessageAnnotationProcessor implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    public static ApplicationContext context;
    public static EventChannel<BotEvent> channel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        MessageAnnotationProcessor.context = context;
    }

    @NotNull
    private EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> c, GroupMessage m) {
        return c.filter(event -> {
            if (m != null && event instanceof GroupMessageEvent event1) {
                boolean flag = true;
                if (!m.filterRule().equals(FilterRule.NONE)) {
                    flag = m.filterRule().equals(FilterRule.BLACKLIST) ? GlobalConfig.isOn
                            && GlobalConfig.groupList.contains(event1.getGroup().getId())
                            && !GlobalConfig.blackList.contains(event1.getSender().getId()) : GlobalConfig.isOn && GlobalConfig.groupList.contains(event1.getGroup().getId());
                }
                switch (m.messageRule()) {
                    case EQUAL -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().equals(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                            }
                            case MASTER -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().contains(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }

    @NotNull
    private EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> c, Message m) {
        return c.filter(event -> {
            if (m != null && event instanceof MessageEvent event1) {
                boolean flag = true;
                if (!m.filterRule().equals(FilterRule.NONE)) {
                    flag = m.filterRule().equals(FilterRule.BLACKLIST) ? GlobalConfig.isOn
                            && !GlobalConfig.blackList.contains(event1.getSender().getId()) : GlobalConfig.isOn;
                }
                switch (m.messageRule()) {
                    case EQUAL -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().equals(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                            }
                            case MASTER -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().contains(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }

    @NotNull
    private EventChannel<BotEvent> toFilter(@NotNull EventChannel<BotEvent> c, UserMessage m) {
        return c.filter(event -> {
            if (m != null && event instanceof UserMessageEvent event1) {
                boolean flag = true;
                if (!m.filterRule().equals(FilterRule.NONE)) {
                    flag = m.filterRule().equals(FilterRule.BLACKLIST) ? GlobalConfig.isOn
                            && !GlobalConfig.blackList.contains(event1.getSender().getId()) : GlobalConfig.isOn;
                }
                switch (m.messageRule()) {
                    case EQUAL -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return event1.getMessage().contentToString().equals(m.text())
                                        && flag;
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().equals(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().startsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().endsWith(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                            }
                            case MASTER -> {
                                return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find()
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        switch (m.permission()) {
                            case WHITE -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.whiteList.contains(event1.getSender().getId());
                            }
                            case ALL -> {
                                return flag && event1.getMessage().contentToString().contains(m.text());
                            }
                            case MASTER -> {
                                return flag && event1.getMessage().contentToString().contains(m.text())
                                        && GlobalConfig.master == event1.getSender().getId();
                            }
                        }
                        return flag && event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }


    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        Bot bot = context.getBean(Bot.class);
        channel = bot.getEventChannel();
        Iterable<String> beanIterable = List.of(context.getBeanDefinitionNames());
        beanIterable.forEach(beanName -> {
            Object bean = context.getBean(beanName);
            System.out.println(beanName);
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            Iterable<Method> methodIterable = List.of(declaredMethods);
            methodIterable.forEach(method -> {
                GroupMessage groupMessage = method.getDeclaredAnnotation(GroupMessage.class);
                UserMessage userMessage = method.getDeclaredAnnotation(UserMessage.class);
                Message message = method.getDeclaredAnnotation(Message.class);
                if (groupMessage != null) {
                    execute(bean, method, toFilter(channel, groupMessage), (GroupMessage) null);
                    return;
                }
                if (userMessage != null) {
                    execute(bean, method, toFilter(channel, userMessage), (UserMessage) null);
                    return;
                }
                if (message != null) {
                    execute(bean, method, toFilter(channel, message), (Message) null);
                }
            });
        });
        bot.login();
    }

    /**
     * @param bean    bean对象
     * @param method  反射获取到的bean对象的方法
     * @param channel 过滤的channel
     * @param message 消息注解
     */
    private void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, Message message) {
        channel.subscribeAlways(MessageEvent.class, event1 -> {
            try {
                method.invoke(bean, event1);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, GroupMessage groupMessage) {
        channel.subscribeAlways(GroupMessageEvent.class, event1 -> {
            try {
                method.invoke(bean, event1);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void execute(Object bean, Method method, @NotNull EventChannel<BotEvent> channel, UserMessage userMessage) {
        channel.subscribeAlways(UserMessageEvent.class, event1 -> {
            try {
                method.invoke(bean, event1);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
