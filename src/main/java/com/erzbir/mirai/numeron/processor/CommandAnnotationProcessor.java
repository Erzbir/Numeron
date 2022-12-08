package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.utils.MiraiLogUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/1 20:34
 */
@Processor
@SuppressWarnings("unused")
public class CommandAnnotationProcessor implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    public final static HashMap<String, Set<String>> helpMap = new HashMap<>();
    public static ApplicationContext context;
    public static StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        MiraiLogUtil.verbose("开始生成命令帮助文档......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> scanBeans(v));
        context.getBeansWithAnnotation(Plugin.class).forEach((k, v) -> scanBeans(v));
        helpMap.forEach((k, v) -> {
            stringBuilder.append(k).append(": \n");
            v.forEach(c -> stringBuilder.append(c));
            stringBuilder.append("\n");
        });
        MiraiLogUtil.verbose("命令帮助文档生成完成");
    }

    private void scanBeans(Object bean) {
        String name = bean.getClass().getName();
        MiraiLogUtil.debug("扫瞄到 " + name);
        List.of(bean.getClass().getDeclaredMethods()).forEach(method -> {
            Command command = method.getDeclaredAnnotation(Command.class);
            if (command != null) {
                Set<String> set = helpMap.computeIfAbsent(command.name(), k -> new HashSet<>());
                set.add(command.dec() + "\n" + command.help() + "\n");
            }
        });
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        CommandAnnotationProcessor.context = context;
    }
}




