package com.erzbir.mirai.numeron.processor;

import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.handler.Plugin;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.utils.MiraiLogUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/1 20:34
 * <p>用于生成指令表, 扫瞄有@Command注解的方法<p/>
 */
@SuppressWarnings("unused")
public class CommandAnnotationProcessor implements Processor {
    private final static HashMap<String, Set<String>> helpMap = new HashMap<>();
    private final static StringBuilder stringBuilder = new StringBuilder();

    public static HashMap<String, Set<String>> getHelpMap() {
        return helpMap;
    }

    private static void addToDocMap(Command command) {
        if (command == null) {
            return;
        }
        Set<String> set = helpMap.computeIfAbsent(command.name(), k -> new HashSet<>());
        set.add(command.dec() + "\n" + command.help() + "\n" + command.permission() + "\n");
    }

    @Override
    public void onApplicationEvent() {
        AppContext context = AppContext.INSTANT;
        MiraiLogUtil.verbose("开始生成命令帮助文档......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> scanBeans(v));
        context.getBeansWithAnnotation(Plugin.class).forEach((k, v) -> scanBeans(v));
        MiraiLogUtil.verbose("命令帮助文档生成完成");
    }

    private void scanBeans(Object bean) {
        String name = bean.getClass().getName();
        MiraiLogUtil.debug("扫瞄到 " + name);
        for (Method method : bean.getClass().getDeclaredMethods()) {
            Command command = method.getDeclaredAnnotation(Command.class);
            addToDocMap(command);
        }
    }
}




