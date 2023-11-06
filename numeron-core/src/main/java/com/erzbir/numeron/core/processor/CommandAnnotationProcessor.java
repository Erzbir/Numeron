package com.erzbir.numeron.core.processor;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.api.context.BeanContext;
import com.erzbir.numeron.api.context.DefaultBeanCentral;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>用于生成指令表, 扫瞄有 {@link  Command} 注解的方法<p/>
 *
 * @author Erzbir
 * @Date: 2022/12/1 20:34
 * @see Processor
 */
@SuppressWarnings("unused")
public class CommandAnnotationProcessor implements Processor {
    @Getter
    private static HashMap<String, Set<String>> helpMap = new HashMap<>();

    private static void addToDocMap(Command command) {
        if (command == null) {
            return;
        }
        Set<String> set = helpMap.computeIfAbsent(command.name(), k -> new HashSet<>());
        set.add(command.dec() + "\n" + command.help() + "\n" + command.permission() + "\n");
    }

    @Override
    public void onApplicationEvent() {
        BeanContext context = DefaultBeanCentral.INSTANCE;
        NumeronLogUtil.info("开始生成命令帮助文档......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> scanBeans(v));
        NumeronImpl numeron = new NumeronImpl();
        StringBuilder stringBuilder = new StringBuilder();
        helpMap.forEach((k, v) -> {
            stringBuilder.append(k).append("\n");
            v.forEach(t -> stringBuilder.append(t).append("\n"));
            stringBuilder.append("---\n");
        });
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(numeron.getWorkDir() + "help.txt"))) {
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
            throw new RuntimeException(e);
        }
        NumeronLogUtil.info("命令帮助文档生成完成\n");
    }

    @Override
    public void destroy() {
        helpMap = new HashMap<>();
    }

    private void scanBeans(Object bean) {
        Class<?> beanClass = bean.getClass();
        String name = beanClass.getName();
        NumeronLogUtil.info("扫瞄到 " + name);
        for (Method method : beanClass.getDeclaredMethods()) {
            Command command = method.getDeclaredAnnotation(Command.class);
            addToDocMap(command);
        }
    }
}




