package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.interfaces.ChannelFilterInter;
import com.erzbir.mirai.numeron.interfaces.PluginRegister;
import kotlin.jvm.internal.Intrinsics;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @author Erzbir
 * @Date: 2022/11/17 09:49
 */
@Component
public class PluginAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    public static ApplicationContext context;
    public static EventChannel<BotEvent> channel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        PluginAnnotationProcessor.context = context;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        Bot bot = MessageAnnotationProcessor.context.getBean(Bot.class);
        channel = bot.getEventChannel();
        Intrinsics.checkNotNull(channel);
        context.getBeansOfType(ChannelFilterInter.class).forEach((k, v) -> channel = channel.filter(v::filter));
        context.getBeansOfType(PluginRegister.class).forEach((k, v) -> v.register(bot, channel));
    }
}
