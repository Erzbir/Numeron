package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.context.DefaultAppContext;
import com.erzbir.numeron.api.processor.Processor;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class SpiStarter extends AbstractStarter implements Starter {
    public SpiStarter(String packageName, ClassLoader classLoader) {
        super(packageName, classLoader);
    }

    public SpiStarter(Class<?> bootClass) {
        super(bootClass);
    }

    public SpiStarter(Class<?> bootClass, ClassLoader classLoader) {
        super(bootClass, classLoader);
    }

    public SpiStarter(String packageName) {
        super(packageName);
    }

    @Override
    public void boot() {
        super.boot();
        ServiceLoader.load(Processor.class).forEach(t -> {
            if (t != null) {
                executor.submit(() -> {
                    t.onApplicationEvent();
                    DefaultAppContext.INSTANCE.addProcessor(t);
                });
            }
        });
        executor.shutdown();
    }
}