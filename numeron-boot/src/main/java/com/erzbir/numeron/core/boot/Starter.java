package com.erzbir.numeron.core.boot;

import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.boot.exception.ProcessorException;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
public class Starter {
    public final ExecutorService executor = Executors.newCachedThreadPool();
    private final String basePackage;
    private final ClassLoader classLoader;

    public Starter(String packageName, ClassLoader classLoader) {
        basePackage = packageName;
        this.classLoader = classLoader;
    }

    public Starter(String packageName) {
        this(packageName, Thread.currentThread().getContextClassLoader());
    }

    public void boot(BotType botType) {
        if (botType.equals(BotType.SPI)) {
            bootFromSpi();
        } else if (botType.equals(BotType.PACKAGE)) {
            bootFromBasePackage();
        }
    }

    private void bootFromBasePackage() {
        ClassScanner scanner = new ClassScanner(basePackage, classLoader, true, null, null);
        try {
            // 扫瞄实现了 Processor 接口的类
            scanner.scanWithInterface(Processor.class).forEach(e -> {
                Processor processor;
                try {
                    processor = (Processor) e.getDeclaredConstructor().newInstance();
                    AppContext.INSTANCE.addProcessor(processor);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    NumeronLogUtil.logger.error("ERROR", ex);
                    throw new ProcessorException(ex);
                }
                processor.onApplicationEvent();
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new ProcessorException(e);
        }
    }

    private void bootFromSpi() {
        ServiceLoader.load(Processor.class).forEach(t -> {
            if (t != null) {
                t.onApplicationEvent();
                AppContext.INSTANCE.addProcessor(t);
            }
        });
    }

    public enum BotType {
        SPI,
        PACKAGE
    }

}