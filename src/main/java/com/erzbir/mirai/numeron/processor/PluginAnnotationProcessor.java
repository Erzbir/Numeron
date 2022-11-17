package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.Interface.ChannelFilterInter;
import com.erzbir.mirai.numeron.Interface.PluginRegister;
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
 * @Author: Erzbir
 * @Date: 2022/11/17 09:49
 */
@Component
public class PluginAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private ApplicationContext applicationContext;
    private EventChannel<BotEvent> channel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        Bot bot = applicationContext.getBean(Bot.class);
        channel = bot.getEventChannel();
        Intrinsics.checkNotNull(channel);
        applicationContext.getBeansOfType(ChannelFilterInter.class).forEach((k, v) -> channel = channel.filter(v::filter));
        applicationContext.getBeansOfType(PluginRegister.class).forEach((k, v) -> v.register(bot, channel));
        bot.login();
    }
}
