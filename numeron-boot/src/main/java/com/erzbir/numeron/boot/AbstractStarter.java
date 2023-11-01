package com.erzbir.numeron.boot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public abstract class AbstractStarter implements Starter {
    protected static final String LOGO = """
                        
            | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
            |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
            | |\\  | |_| | | | | | |  __/ | | (_) | | | |
            |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|
                        
            """;
    protected final ExecutorService executor = Executors.newCachedThreadPool();
    protected final String basePackage;
    protected final ClassLoader classLoader;

    protected AbstractStarter(Class<?> bootClass) {
        this(bootClass.getPackageName());
    }

    protected AbstractStarter(Class<?> bootClass, ClassLoader classLoader) {
        this(bootClass.getPackageName(), classLoader);
    }

    protected AbstractStarter(String packageName, ClassLoader classLoader) {
        basePackage = packageName;
        this.classLoader = classLoader;
    }

    protected AbstractStarter(String packageName) {
        this(packageName, Thread.currentThread().getContextClassLoader());
    }
}
