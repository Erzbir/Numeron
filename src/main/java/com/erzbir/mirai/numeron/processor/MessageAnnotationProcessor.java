package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.Annotation.GroupMessage;
import com.erzbir.mirai.numeron.Annotation.Message;
import com.erzbir.mirai.numeron.Annotation.UserMessage;
import com.erzbir.mirai.numeron.Interface.ChannelFilterInter;
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
    public static EventChannel<BotEvent> filteredChannel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        MessageAnnotationProcessor.context = context;
    }

    private EventChannel<BotEvent> toFilter(EventChannel<BotEvent> c, GroupMessage m) {
        return c.filter(event -> {
            if (m != null && event instanceof GroupMessageEvent event1) {
                switch (m.messageRule()) {
                    case EQUAL -> {
                        return event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        return event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        return event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        return Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        return event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }

    private EventChannel<BotEvent> toFilter(EventChannel<BotEvent> c, Message m) {
        return c.filter(event -> {
            if (m != null && event instanceof MessageEvent event1) {
                switch (m.messageRule()) {
                    case EQUAL -> {
                        return event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        return event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        return event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        return Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        return event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }

    private EventChannel<BotEvent> toFilter(EventChannel<BotEvent> c, UserMessage m) {
        return c.filter(event -> {
            if (m != null && event instanceof UserMessageEvent event1) {
                switch (m.messageRule()) {
                    case EQUAL -> {
                        return event1.getMessage().contentToString().equals(m.text());
                    }
                    case BEGIN_WITH -> {
                        return event1.getMessage().contentToString().startsWith(m.text());
                    }
                    case END_WITH -> {
                        return event1.getMessage().contentToString().endsWith(m.text());
                    }
                    case REGEX -> {
                        return Pattern.compile(m.text()).matcher(event1.getMessage().contentToString()).find();
                    }
                    case CONTAINS -> {
                        return event1.getMessage().contentToString().contains(m.text());
                    }
                }
            }
            return false;
        });
    }


    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        Bot bot = PluginAnnotationProcessor.context.getBean(Bot.class);
        channel = bot.getEventChannel();
        filteredChannel = bot.getEventChannel();
        context.getBeansOfType(ChannelFilterInter.class).forEach((k, v) -> filteredChannel = filteredChannel.filter(v::filter));
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
                if (message != null) {
                    execute(bean, method, toFilter(filteredChannel, message), toFilter(channel, message), message);
                    return;
                }
                if (groupMessage != null) {
                    execute(bean, method, toFilter(filteredChannel, groupMessage), toFilter(channel, groupMessage), groupMessage);
                    return;
                }
                if (userMessage != null) {
                    execute(bean, method, toFilter(filteredChannel, userMessage), toFilter(channel, userMessage), userMessage);
                }
            });
        });
    }

    /**
     *
     * @param bean bean对象
     * @param method 反射获取到的bean对象的方法
     * @param channel1 过滤后的channel
     * @param channel2 没有过滤的channel
     * @param message 消息注解
     */
    private void execute(Object bean, Method method, EventChannel<BotEvent> channel1, EventChannel<BotEvent> channel2, @NotNull Message message) {
        if (message.filter()) {
            channel1.subscribeAlways(MessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } else {
            channel2.subscribeAlways(MessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void execute(Object bean, Method method, EventChannel<BotEvent> channel1, EventChannel<BotEvent> channel2, @NotNull GroupMessage groupMessage) {
        if (groupMessage.filter()) {
            channel1.subscribeAlways(GroupMessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } else {
            channel2.subscribeAlways(GroupMessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void execute(Object bean, Method method, EventChannel<BotEvent> channel1, EventChannel<BotEvent> channel2, @NotNull UserMessage userMessage) {
        if (userMessage.filter()) {
            channel1.subscribeAlways(UserMessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } else {
            channel2.subscribeAlways(UserMessageEvent.class, event1 -> {
                try {
                    method.invoke(bean, event1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
