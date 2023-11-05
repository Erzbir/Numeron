package com.erzbir.numeron.boot;

import com.erzbir.numeron.annotation.Component;
import com.erzbir.numeron.api.context.DefaultBeanCentral;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public abstract class AbstractStarter implements Starter {
    protected final ExecutorService executor = Executors.newCachedThreadPool();
    protected final String packageName;
    protected final ClassLoader classLoader;

    protected AbstractStarter(Class<?> bootClass) {
        this(bootClass.getPackageName());
    }

    protected AbstractStarter(Class<?> bootClass, ClassLoader classLoader) {
        this(bootClass.getPackageName(), classLoader);
    }

    protected AbstractStarter(String packageName, ClassLoader classLoader) {
        this.packageName = packageName;
        this.classLoader = classLoader;
    }

    protected AbstractStarter(String packageName) {
        this(packageName, Thread.currentThread().getContextClassLoader());
    }

    @Override
    public void boot() {
        DefaultBeanCentral.INSTANCE.plusByAnnotation(packageName, classLoader, Component.class);
    }
}
