package com.erzbir.numeron.boot;

import com.erzbir.numeron.boot.exception.ProcessorException;
import com.erzbir.numeron.core.classloader.ClassScanner;
import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.processor.Processor;

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
    private final ClassLoader classLoader;

    public Starter(String packageName, ClassLoader classLoader) {
        basePackage = packageName;
        this.classLoader = classLoader;
    }

    public Starter(String packageName) {
        this(packageName, Thread.currentThread().getContextClassLoader());
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
        ClassScanner scanner = new ClassScanner(basePackage, classLoader, true, null, null);
        try {
            // 扫瞄实现了Processor接口的类
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
