package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.LogUtil.MiraiLogUtil;
import com.erzbir.mirai.numeron.filter.PluginChannelFilterInter;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/17 09:49
 * <p>
 * 这个类的逻辑和作用和MessageAnnotationProcessor的逻辑类似, 这个类用来给插件注册和过滤
 * </p>
 */
@Processor
@SuppressWarnings("unused")
public class PluginAnnotationProcessor implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    public static ApplicationContext context;
    public static EventChannel<BotEvent> channel;
    public static Bot bot;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        PluginAnnotationProcessor.context = context;
    }

    /**
     * 此方法扫瞄实现了规定接口的类
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        bot = context.getBean(Bot.class);
        channel = bot.getEventChannel();
        MiraiLogUtil.info("开始过滤插件监听......");
        // 扫瞄插件过滤器
        context.getBeansOfType(PluginChannelFilterInter.class).forEach((k, v) -> {
            MiraiLogUtil.info("扫瞄到过滤器 " + k);
            channel = channel.filter(v::filter);
        });
        MiraiLogUtil.info("插件监听过滤完成\n");
        MiraiLogUtil.info("开始注册插件监听.......");
        // 扫瞄插件
        context.getBeansOfType(PluginRegister.class).forEach((k, v) -> {
            MiraiLogUtil.info("扫瞄到插件 " + k);
            v.register(bot, channel);
        });
        MiraiLogUtil.info("插件监听注册完毕\n");
    }
}
