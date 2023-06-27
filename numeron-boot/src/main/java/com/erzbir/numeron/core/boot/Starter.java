package com.erzbir.numeron.core.boot;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.console.bot.BotLoader;
import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;
import com.erzbir.numeron.core.boot.exception.ProcessorException;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.Bot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
public class Starter {
    private static final String LOGO = """
                        
            | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
            |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
            | |\\  | |_| | | | | | |  __/ | | (_) | | | |
            |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|
                        
            """;
    public final ExecutorService executor = Executors.newCachedThreadPool();
    public final Initial initial = new Initial();
    private final String basePackage;
    private final ClassLoader classLoader;

    public Starter() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public Starter(String packageName, ClassLoader classLoader) {
        basePackage = packageName;
        this.classLoader = classLoader;
    }

    public Starter(String packageName) {
        this(packageName, Thread.currentThread().getContextClassLoader());
    }

    public Starter(ClassLoader classLoader) {
        this("com.erzbir.numeron", classLoader);
    }

    public void boot(BotType botType) throws InterruptedException {
        long l = System.currentTimeMillis();
        initial.initConfig();
        initial.initPlugin();
        if (botType.equals(BotType.SPI)) {
            bootFromSpi();
        } else if (botType.equals(BotType.PACKAGE)) {
            bootFromBasePackage();
        }
        initial.initLogin();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
        l = System.currentTimeMillis() - l;
        while (!initial.loginExecutor.isTerminated()) {
            Thread.sleep(1000);
        }
        printLog(LOGO);
        NumeronLogUtil.info("欢迎使用 Numeron!!!");
        NumeronLogUtil.info("启动耗时: " + l + "ms");
        BotServiceImpl.INSTANCE.getBotList().forEach(Bot::join);
    }

    private void bootFromBasePackage() {
        ClassScanner scanner = new ClassScanner(basePackage, classLoader, true, null);
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
                executor.submit(processor::onApplicationEvent);
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new ProcessorException(e);
        } finally {
            executor.shutdown();
        }
    }

    private void bootFromSpi() {
        ServiceLoader.load(Processor.class).forEach(t -> {
            if (t != null) {
                executor.submit(() -> {
                    t.onApplicationEvent();
                    AppContext.INSTANCE.addProcessor(t);
                });
            }
        });
        executor.shutdown();
    }

    private void printLog(String logo) {
        NumeronLogUtil.info(logo.indent(1));
    }

    public enum BotType {
        SPI,
        PACKAGE
    }

    static class Initial {
        final ExecutorService loginExecutor = Executors.newFixedThreadPool(4);

        void initPlugin() {
            PluginManager pluginManager = PluginManager.INSTANCE;
            List<Plugin> plugins = pluginManager.getPlugins();
            plugins.forEach(plugin -> {
                NumeronLogUtil.info("插件: " + plugin.getDescription().getName());
                PluginManager.INSTANCE.enable(plugin);
            });
        }

        void initLogin() {
            loginExecutor.submit(() -> BotServiceImpl.INSTANCE.getBotList().forEach(t -> BotServiceImpl.INSTANCE.login(t)));
            loginExecutor.shutdown();
        }

        void initConfig() {
            BotLoader.load();
        }
    }
}
