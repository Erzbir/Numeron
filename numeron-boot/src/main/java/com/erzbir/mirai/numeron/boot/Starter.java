package com.erzbir.mirai.numeron.boot;

import com.erzbir.mirai.numeron.boot.exception.ProcessorException;
import com.erzbir.mirai.numeron.classloader.ClassScanner;
import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.processor.Processor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
public class Starter {
    static {
        NumeronBot numeronBot = NumeronBot.INSTANCE;  // 这里是为了提前初始化, 不然会出现空指针异常
    }

    private final String basePackage;

    public Starter(@NotNull String packageName) {
        basePackage = packageName;
    }

    public void boot() {
        bootFromBasePackage();
    }

    private void bootFromBasePackage() {
        // 包扫瞄器扫瞄所有class
        scanProcessor();
        NumeronBot.INSTANCE.login();
    }

    private void scanProcessor() {
        ClassScanner scanner = new ClassScanner(basePackage, true, t -> true, null);
        try {
            // 扫瞄实现了{@code Processor}接口的类
            scanner.scanWithInterface(Processor.class).forEach(e -> {
                Processor processor;
                try {
                    processor = (Processor) e.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    throw new ProcessorException(ex);
                }
                processor.onApplicationEvent();
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new ProcessorException(e);
        }
    }
}
